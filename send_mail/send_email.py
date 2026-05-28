import smtplib
from datetime import datetime, timezone
from email.message import EmailMessage
from email.utils import format_datetime

smtp_host = "localhost"
smtp_port = 1025

citrus_mail_from = "sender@fakemail.com"
citrus_mail_to = ["receiver@fakemail.com"]
citrus_mail_cc = ["cc1@fakemail.com", "cc2@fakemail.com"]
citrus_mail_bcc = ["bcc1@fakemail.com"]

citrus_mail_subject = "Mail Test"
citrus_mail_replyTo = "replyto@fakemail.com"
citrus_mail_date = format_datetime(datetime.now(timezone.utc))

body = "This is the Email Test Text"

msg = EmailMessage()


msg["From"] = citrus_mail_from
msg["To"] = ", ".join(citrus_mail_to)
msg["Cc"] = ", ".join(citrus_mail_cc)
msg["Bcc"] = ", ".join(citrus_mail_bcc)
msg["Subject"] = citrus_mail_subject
msg["Reply-To"] = citrus_mail_replyTo
msg["Date"] = citrus_mail_date
msg.set_content(body)


all_recipients = citrus_mail_to + citrus_mail_cc + citrus_mail_bcc

with smtplib.SMTP(smtp_host, smtp_port) as server:
    server.send_message(msg, from_addr=citrus_mail_from, to_addrs=all_recipients)

print("Email sent")
