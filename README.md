[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.gonguasp/jwt/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.gonguasp/jwt)

# Jwt
Jwt validator

### How it works:
With the annotation @AccessTokenVerification before a 
method controller it will validate a jwt token in the
request header called by default "accessToken".

The default values the jwt validator will use are: 
password: "secret" as the password to generate the jwt token;
expiration: "10" hours the jwt token will expire;
access-token: "accessToken" as header name where the jwt token must be.

It is possible to change these default values adding the following keys
to the application.properties:
jwt.password=< password >, jwt.expiration=< expiration >,
jwt.access-token=< access-token >

The class JwtService has multiple methods to generate, validate 
and extract all or specific claims from a jwt token.