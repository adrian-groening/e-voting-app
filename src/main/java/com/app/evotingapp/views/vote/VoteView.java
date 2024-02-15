package com.app.evotingapp.views.vote;

import com.app.evotingapp.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.app.evotingapp.services.FirebaseService;
import com.app.evotingapp.services.MailService;

@PageTitle("Vote")
@Route(value = "vote", layout = MainLayout.class)
public class VoteView extends VerticalLayout {

    H2 loginHeader = new H2("Log In");
    H2 signUpHeader = new H2("Sign Up");
    
    private TextField name = new TextField("Full Name (e.g. John Doe)");
    private EmailField email = new EmailField("Email (e.g. name@example.com)");
    private PasswordField password = new PasswordField("Password (Min. 8 characters w/ at least 1 letter & number)");
    private TextField id = new TextField("ID (SA ID Number)");
    private Button signUpButton = new Button("Sign Up");
    private FormLayout formLayout = new FormLayout(name, email, password, id);

    //components for logging in
    private TextField emailLogIn = new TextField("Email");
    private PasswordField passwordLogIn = new PasswordField("Password");
    private Button loginButton = new Button("Log In");
    private FormLayout loginFormLayout = new FormLayout(emailLogIn, passwordLogIn);

    FirebaseService firebaseService = new FirebaseService();


    public VoteView() {
        setWidth("100%");
        getStyle().set("flex-grow", "1");

        

        addSignUp();
        addLogin();

        signUpButton.addClickListener(click -> {
            MailService mailService = new MailService(email.getValue());
            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || id.isEmpty()) {
                Notification.show("Fill in all the fields");
            } else {
                try {
                    if(mailService.checkEmail()) {
                        if(name.isInvalid()) {
                            Notification.show("Invalid name");
                        } else if(password.isInvalid()) {
                            Notification.show("Password is invalid");
                        } else if(id.isInvalid()) {
                            Notification.show("ID is invalid");
                        } else {
                            if(firebaseService.userExists(id.getValue())) {
                                Notification.show("User already exists");
                            } else {
                                Notification.show("User does not exist");
                            }

                            //check if id exists in database
                            //if doesnt exist, add to database
                            //hash password when adding to database
                            //remove sign up and login form
                            //add voting interface
                        }

                    } else {
                        Notification.show("Email is disposable");
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }                

            }
        }); 
        
        loginButton.addClickListener(click -> {
            //check for email and password validity
            //if valid, add voting interface
        });
    }

    public void addSignUp() {
        signUpButton.setWidthFull();
        formLayout.setWidth("100%");

        addClassName("sign-up-view");
        setSizeFull();  
        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        getStyle().set("flex-grow", "1");
        setJustifyContentMode(JustifyContentMode.START);

        name.setPattern("^([a-zA-z,/.-]+)\\s([a-zA-z,/.-]+)$");
        name.setRequiredIndicatorVisible(true);

        email.setRequiredIndicatorVisible(true);

        password.setPattern("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        password.setRequiredIndicatorVisible(true);

        id.setPattern("^[0-9]{13}$");
        id.setRequiredIndicatorVisible(true);


        add(signUpHeader, formLayout); // Add the form layout to the view
        add(signUpButton);    
    }

    public void addLogin() {
        loginButton.setWidthFull();
        loginFormLayout.setWidth("100%");

        addClassName("login-view");
        setSizeFull(); // Set the size of the view to fill the entire screen
        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        getStyle().set("flex-grow", "1");
        setJustifyContentMode(JustifyContentMode.START);
    
        add(loginHeader, loginFormLayout); // Add the login form layout to the view
        add(loginButton);
    }

    public void removeSignUp() {
        remove(formLayout);
        remove(signUpButton);
        remove(signUpHeader);
    }

    public void removeLogin() {
        remove(loginFormLayout);
        remove(loginButton);
        remove(loginHeader);
    }

}
