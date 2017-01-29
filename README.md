# cybersecuritybase-project

Spring application based on the course template, clone or download and run in Netbeans.

#### Available pages
These aren't linked anywhere, sorry

- Login page: localhost:8080/login
- Logout: /logout
- Register a new user: /register
- View messages: /messages
- View secret notes: /secrets
- View a single secret: /secrets/{secret id}
- Signup: /signup

## Flaws:
Flaws are numbered according to the [OWASP top 10 list](https://www.owasp.org/index.php/Top_10_2013-Top_10)

### XSS and broken session management (A2 and A3)
1 - Go to localhost:8080/register

2 - Log in with user "ted" and password "nosecurity"

3 - Go to localhost:8080/messages

4 - Copy the following script into the text box:


    <script>
      alert(document.cookie);
    </script>

5 - Click send

6 - You should now see an alert showing your session id. The script could send this anywhere.

##### Fixes
This vulnerability can be fixed by making session cookies http-only and using regular escaped th:text -tags in the Thymeleaf template instead of unescaped th:utext.

Tomcat makes sessionid-cookies http-only by default, so the vulnerability is fixed by deleting CookieConfiguration.java in sec.project.config

### Insecure Direct Object References (A4)

All secrets can be accessed by anyone with direct links.

1 - Sign in as ted

2 - Log in and go to /secrets

3 - Create a new secret

4 - A link to the secret should appear. Click it and copy the address.

5 - Log out at /logout

6 - create a new user at /register

7 - log in as the new user

8 - go to the address of the secret you copied. For example localhost:8080/secrets/2

9 - You should be able to see the secret.

##### Fix
The SecretController should check the identity of the viewer before showing the secret.

### Security misconfiguration, Sensitive data exposure and CSRF protection (A5, A6 and A8)

* Data is saved in an insecure manner. Secrets and passwords should be saved in encrypted format.
* CSRF protection is missing.

View SecurityConfiguration.java at sec.project.config

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

Passwords are saved in plaintext. This is stupid and easy to fix by using BCrypt password encoder instead:

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

CSRF protection can be enabled by removing the line

    http.csrf().disable();

### Missing function level access control (A7)
The application doesn't have any access control for viewing and deleting secret notes.

No access control here: (SecretController.java)

      @ResponseBody
      @RequestMapping(value = "/secrets/{id}", method = equestMethod.GET)
      public String show(@PathVariable Long id){
        SecretNote secret = secretNoteRepository.findOne(id);
        return secret.toString();
      }

      @RequestMapping(value = "/secrets/{id}", method = R  equestMethod.DELETE)
      public String delete(@PathVariable Long id){
        secretNoteRepository.delete(id);
        return "redirect:/secrets";
      }

This could be fixed by checking that the request to view or delete is coming either from the owner or an admin when deleting or showing secrets.

### Others

* Usernames are not checked to be unique. Creating a new account with an existing username is allowed but makes it impossible to log in with either account. This makes the older account lose access to important data.
  * Fixed by adding the @Unique-annotation to username in Account.java
