package codes.wilma24.Skype.v1_0_R1.pgp;

import static java.lang.System.getProperty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.pgpainless.PGPainless;
import org.pgpainless.algorithm.DocumentSignatureType;
import org.pgpainless.algorithm.KeyFlag;
import org.pgpainless.decryption_verification.ConsumerOptions;
import org.pgpainless.decryption_verification.DecryptionStream;
import org.pgpainless.decryption_verification.MessageMetadata;
import org.pgpainless.encryption_signing.EncryptionOptions;
import org.pgpainless.encryption_signing.EncryptionStream;
import org.pgpainless.encryption_signing.ProducerOptions;
import org.pgpainless.encryption_signing.SigningOptions;
import org.pgpainless.exception.KeyException;
import org.pgpainless.key.generation.KeySpec;
import org.pgpainless.key.generation.type.ecc.EllipticCurve;
import org.pgpainless.key.generation.type.ecc.ecdh.ECDH;
import org.pgpainless.key.generation.type.ecc.ecdsa.ECDSA;
import org.pgpainless.key.generation.type.rsa.RSA;
import org.pgpainless.key.generation.type.rsa.RsaLength;
import org.pgpainless.key.protection.SecretKeyRingProtector;

import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.v1_0_R1.data.types.Conversation;
import codes.wilma24.Skype.v1_0_R1.data.types.Message;
import codes.wilma24.Skype.v1_0_R1.forms.MainForm;

public class PGPUtilities {

	private static File getUserDir() {
		String workingDirectory;
		String OS = (getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN")) {
			workingDirectory = System.getenv("AppData");
		} else {
			workingDirectory = getProperty("user.home");
		}
		File userDir = new File(workingDirectory, "Skype");
		userDir.mkdirs();
		return userDir;
	}

	private static File getUserFile(String fileName) {
		return new File(getUserDir(), fileName);
	}

	public static PGPSecretKeyRing createOrLookupPrivateKey(String skypeName)
			throws IOException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, PGPException {
		File privateKeyFile = getUserFile(skypeName + ".key");
		if (!privateKeyFile.exists()) {
			PGPSecretKeyRing privateKey = PGPainless
					.buildKeyRing()
					.setPrimaryKey(
							KeySpec.getBuilder(RSA.withLength(RsaLength._4096),
									KeyFlag.SIGN_DATA, KeyFlag.CERTIFY_OTHER))
					.addSubkey(
							KeySpec.getBuilder(
									ECDSA.fromCurve(EllipticCurve._P256),
									KeyFlag.SIGN_DATA))
					.addSubkey(
							KeySpec.getBuilder(
									ECDH.fromCurve(EllipticCurve._P256),
									KeyFlag.ENCRYPT_COMMS,
									KeyFlag.ENCRYPT_STORAGE))
					.addUserId(skypeName + " <" + skypeName + "@hookipa.net>")
					.build();
			String privateKeyArmoured = PGPainless.asciiArmor(privateKey);
			FileOutputStream fos = new FileOutputStream(privateKeyFile);
			fos.write(privateKeyArmoured.getBytes());
			fos.flush();
			fos.close();
			System.out.println(privateKeyArmoured);
		}
		return PGPainless.readKeyRing().secretKeyRing(
				new String(Files.readAllBytes(privateKeyFile.toPath())));
	}

	public static String createOrLookupPublicKey(String skypeName)
			throws InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, IOException, PGPException {
		PGPSecretKeyRing secretKey = createOrLookupPrivateKey(skypeName);
		PGPPublicKeyRing secretKeyPubKey = PGPainless
				.extractCertificate(secretKey);
		String publicKeyArmoured = PGPainless.asciiArmor(secretKeyPubKey);
		System.out.println(publicKeyArmoured);
		return publicKeyArmoured;
	}

	public static String encryptAndSign(String arg0, Conversation arg1) {
		try {
			PGPSecretKeyRing secretKey = createOrLookupPrivateKey(MainForm
					.get().getLoggedInUser().getSkypeName());
			if (arg1.isGroupChat()) {
				List<PGPPublicKeyRing> pubKeys = new ArrayList<>();
				for (UUID participantId : arg1.getParticipants()) {
					Optional<Conversation> userLookup = MainForm.get()
							.lookupUser(participantId);
					if (userLookup.isPresent()) {
						Optional<PGPPublicKeyRing> pubKey = userLookup.get()
								.getPubKey();
						if (pubKey.isPresent()) {
							pubKeys.add(pubKey.get());
						}
					}
				}
				if (pubKeys.size() > 0) {
					return encryptAndSign(arg0, secretKey,
							pubKeys.toArray(new PGPPublicKeyRing[0]));
				}
			} else {
				Optional<PGPPublicKeyRing> pubKey = arg1.getPubKey();
				if (pubKey.isPresent()) {
					return encryptAndSign(arg0, secretKey, pubKey.get());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arg0;
	}

	public static DecryptionResult decryptAndVerify(String arg0,
			Conversation arg1) {
		try {
			PGPSecretKeyRing secretKey = createOrLookupPrivateKey(MainForm
					.get().getLoggedInUser().getSkypeName());
			Optional<PGPPublicKeyRing> pubKey = arg1.getPubKey();
			if (pubKey.isPresent()) {
				return decryptAndVerify(arg0, secretKey,
						pubKey.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DecryptionResult(arg0, false, false);
	}

	public static String encryptAndSign(String arg0,
			PGPSecretKeyRing secretKey, PGPPublicKeyRing... pubKeys)
			throws KeyException, PGPException, IOException {
		PGPPublicKeyRing secretKeyPubKey = PGPainless
				.extractCertificate(secretKey);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		EncryptionOptions options = new EncryptionOptions()
				.addRecipient(secretKeyPubKey);
		for (PGPPublicKeyRing pubKey : pubKeys) {
			options.addRecipient(pubKey);
		}
		EncryptionStream encryptionStream = PGPainless
				.encryptAndOrSign()
				.onOutputStream(baos)
				.withOptions(
						ProducerOptions
								.signAndEncrypt(
										options,
										new SigningOptions().addInlineSignature(
												SecretKeyRingProtector
														.unprotectedKeys(),
												secretKey,
												DocumentSignatureType.CANONICAL_TEXT_DOCUMENT))
								.setAsciiArmor(true));

		encryptionStream.write(arg0.getBytes());
		encryptionStream.close();

		return new String(baos.toByteArray());
	}

	public static DecryptionResult decryptAndVerify(String arg0,
			PGPSecretKeyRing secretKey, PGPPublicKeyRing pubKey)
			throws PGPException, IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(arg0.getBytes());
		ConsumerOptions options = new ConsumerOptions().addDecryptionKey(
				secretKey, SecretKeyRingProtector.unprotectedKeys());
		options.addVerificationCert(pubKey);
		DecryptionStream decryptionStream = PGPainless.decryptAndOrVerify()
				.onInputStream(bais).withOptions(options);

		byte[] b = new byte[1024];
		int bytesRead;
		StringBuilder sb = new StringBuilder();
		while ((bytesRead = decryptionStream.read(b)) != -1) {
			sb.append(new String(Arrays.copyOf(b, bytesRead)));
		}

		decryptionStream.close();

		MessageMetadata metadata = decryptionStream.getMetadata();

		if (metadata.isVerifiedSignedBy(pubKey)) {
			return new DecryptionResult(sb.toString(), true, true);
		} else {
			return new DecryptionResult(sb.toString(), true, false);
		}
	}

	public static class DecryptionResult {

		private String message;

		private boolean successful, signatureVerified;

		public DecryptionResult(String message, boolean successful,
				boolean signatureVerified) {
			this.setMessage(message);
			this.setSuccessful(successful);
			this.setSignatureVerified(signatureVerified);
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isSuccessful() {
			return successful;
		}

		public void setSuccessful(boolean successful) {
			this.successful = successful;
		}

		public boolean isSignatureVerified() {
			return signatureVerified;
		}

		public void setSignatureVerified(boolean signatureVerified) {
			this.signatureVerified = signatureVerified;
		}

	}

}
