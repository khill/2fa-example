# 2fa-example
Example spring boot application showing two-factor authentication.

All of this code is really just an implementation of the process descibed here:

[https://www.baeldung.com/spring-security-two-factor-authentication-with-soft-token](https://www.baeldung.com/spring-security-two-factor-authentication-with-soft-token)

so full credit to Baeldung.  If you are looking for a tighter Spring Security integration, you can find it in their project [https://github.com/Baeldung/spring-security-registration](here).

This is a basic application for demonstrating how to use the [Aerogear Library](https://aerogear.org/docs/guides/AeroGear-OTP/) to generate a one-time password and then display a QR code which can be used to import the information into an authenticator app.

The QR code is generated via the [Google Chart API](https://developers.google.com/chart) and the application is build as a simple Spring boot app.

This is just a simple example to demonstrate how to use Aerogear and Google Charts.  It is not production-ready code and is not secure so please don't cut-n-paste this into your critical applications.
