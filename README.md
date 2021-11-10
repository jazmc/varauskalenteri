# varauskalenteri
## Palvelinohjelmointi 2021 harjoitustyö

*Java + Spring Boot + Thymeleaf + jQuery -verkkosovellus*

-------

Sovellus pyörii livenä osoitteessa https://varauskalenteri.herokuapp.com

### Kirjautuminen
* Testiadmin **admin**, salasana admin
* Testikäyttäjät **user**, **user1**, **user2**, **user3**, kaikkien näiden salasana user

### Ominaisuuksia
* Lentovarauksia voi tehdä vain nykyhetkeen tai tulevaisuuteen
* Lentovarauksen minimikesto on 1 h, kestoa säädetään puolen tunnin inkrementeissä
* [TODO:] Tavallinen "user" voi tehdä vain *lentovaraus*-kategorian varauksia, "admin" voi tehdä myös *huolto*-varauksia
* Tavallinen "user" voi muokata ja poistaa vain omia varauksiaan, "admin" voi muokata ja poistaa kaikkia
* Jos kalenterihetkellä on varaus joka päättyy puolituntiseen, järjestelmä ehdottaa aloitusta puolituntisesta
* Varaukset on värikoodattu (huolto on harmaa, loput väritetään user-id:n mukaan)
* Varauskalenteria voi selata siirtymällä kuukausibuttoneista, tai kirjoittamalla osoiteriville halutun osoitteen muodossa `?kk=X&v=YYYY`


