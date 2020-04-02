package com.letswork.api.token.domain;

import com.letswork.api.token.domain.exception.InvalidTokenException;
import com.letswork.api.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
class TokenService implements EmailSender {

    private final TokenRepository repository;
    private final TokenFactory factory;
    private final JavaMailSender mailSender;

    public void sendRegisterConfirmationToken(UserEntity user) {
        try {
            TokenEntity token = factory.create(user);
            sendEmail(user.getEmail(), token.getConfirmationToken());
            repository.save(token);
        } catch (Exception e) {
            throw new InvalidTokenException(InvalidTokenException.CAUSE.CONFIRMATION_TOKEN_NOT_SENT);
        }
    }

    @Override
    public void sendEmail(String to, String confirmationToken) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        String htmlMsg = "Aby potwierdzić konto, kliknij proszę na poniższy link:<br>" +
                "<a href='http://localhost:3000/confirm-account?token=" + confirmationToken + "'>" +
                "http://localhost:3000/confirm-account?token=" + confirmationToken + "</a>";
        mimeMessage.setContent(htmlMsg, "text/html");
        helper.setTo(to);
        helper.setSubject("Dokończenie rejestracji");
        helper.setFrom("LetsWork");
        mailSender.send(mimeMessage);
    }

    public TokenEntity findTokenByConfirmationToken(String confirmationToken) {
        cleanAllExpiredTokens();
        TokenEntity token = repository.findByConfirmationToken(confirmationToken);
        if (token == null) {
            throw new InvalidTokenException(InvalidTokenException.CAUSE.CONFIRMATION_TOKEN_EXPIRED);
        }
        return token;
    }

    private void cleanAllExpiredTokens() {
        repository.findAll()
                .stream()
                .map(token -> repository.findById(token.getId()))
                .filter(token -> (getCurrentTimeInSeconds() - Long.parseLong(token.getDateInSeconds())) > 900)
                .forEach(repository::delete);
    }

    private long getCurrentTimeInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis()));
    }

    public void deleteToken(TokenEntity token) {
        repository.delete(token);
    }
}
