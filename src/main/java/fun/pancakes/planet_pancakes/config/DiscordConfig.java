package fun.pancakes.planet_pancakes.config;

import lombok.extern.slf4j.Slf4j;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DiscordConfig {

    @Value("${discord.bot.token}")
    private String token;

    @Bean
    public DiscordApi discordApi() {
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        log.info("Discord bot can be invited with the following uri: :" + api.createBotInvite());
        return api;
    }
}
