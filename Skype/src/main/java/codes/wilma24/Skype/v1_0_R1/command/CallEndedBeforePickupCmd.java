package codes.wilma24.Skype.v1_0_R1.command;

import java.awt.event.WindowEvent;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInCallEndedBeforePickup;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.v1_0_R1.forms.IncomingCallForm;
import codes.wilma24.Skype.v1_0_R1.forms.MainForm;

public class CallEndedBeforePickupCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		// TODO Display on screen that this person has left the phone call
		PacketPlayInCallEndedBeforePickup packet = Packet.fromJson(
				msg.toString(), PacketPlayInCallEndedBeforePickup.class);
		UUID callId = packet.getCallId();
		for (IncomingCallForm incomingCallForm : MainForm.get().incomingCallForms) {
			if (incomingCallForm.callId.equals(callId)) {
				incomingCallForm.dispatchEvent(new WindowEvent(
						incomingCallForm, WindowEvent.WINDOW_CLOSING));
				MainForm.get().incomingCallForms.remove(incomingCallForm);
			}
		}
		return PacketPlayInReply.empty();
	}

}
