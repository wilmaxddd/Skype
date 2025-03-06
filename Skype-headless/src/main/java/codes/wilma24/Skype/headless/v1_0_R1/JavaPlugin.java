/*
 * Decompiled with CFR 0.152.
 */
package codes.wilma24.Skype.headless.v1_0_R1;

import codes.wilma24.Skype.headless.v1_0_R1.Conversation;
import codes.wilma24.Skype.headless.v1_0_R1.Skype;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Message;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Status;

public abstract class JavaPlugin
implements Skype.IncomingMessageCallback,
Skype.DeletedMessageCallback {
    public String hostname;
    public String skypeName;
    public String password;
    public Skype bot;

    public JavaPlugin(String hostname, String skypeName, String password) {
        this.hostname = hostname;
        this.skypeName = skypeName;
        this.password = password;
        this.bot = Skype.getPlugin();
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getSkypeName() {
        return this.skypeName;
    }

    public String getPassword() {
        return this.password;
    }

    public Skype getBot() {
        return this.bot;
    }

    public void onEnable() {
        this.bot.setHostname(this.hostname);
        this.bot.createHandle();
        this.bot.register(this.skypeName, this.password);
        this.bot.login(this.skypeName, this.password);
        this.bot.getLoggedInUser().setOnlineStatus(Status.ONLINE);
        this.bot.getLoggedInUser().setDisplayName(this.skypeName);
        this.bot.saveUser();
        this.bot.addIncomingMessageCallback(this);
        this.bot.addDeletedMessageCallback(this);
    }

    public void onDisable() {
        this.bot.onDisable();
    }

    @Override
    public abstract void onMsgSend(Conversation var1, Conversation var2, Message var3);

    @Override
    public abstract void onMsgDelete(Conversation var1, Conversation var2, Message var3);
}

