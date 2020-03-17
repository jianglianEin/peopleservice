package com.microservice.peopleservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service


@Service
class MailService {
    @Autowired
    private lateinit var mailSender: JavaMailSender
    @Value("\${microservice.peopleservice.url}")
    lateinit var peopleServiceUrl: String

    @Async
    fun sendEmailToInviteReceiver(receiverMail: String, announcer: String, teamId: Int) {
        val message = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message, true)
        messageHelper.setFrom("913057041@qq.com")
        messageHelper.setTo(receiverMail)
        messageHelper.setSubject("Join team message:")
        messageHelper.setText("Hello!, dear $receiverMail!\n" +
                "$announcer invite you to join $announcer's team!\n" +
                "if you want to work with $announcer, " +
                "please click following link:\n"+
                "$peopleServiceUrl/team/handleUser?" +
                "teamId=$teamId&username=$announcer&isAdd=true")

        mailSender.send(message)
    }
}