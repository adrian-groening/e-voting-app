package com.app.evotingapp.views.vote;

import com.app.evotingapp.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.app.evotingapp.Entities.candidate;
import com.app.evotingapp.Entities.user;
import com.app.evotingapp.services.FirebaseService;
import com.app.evotingapp.services.MailService;
import com.app.evotingapp.services.PasswordService;

@PageTitle("Vote")
@Route(value = "vote", layout = MainLayout.class)
public class VoteView extends Main implements HasComponents, HasStyle {

    H2 loginHeader = new H2("Log In");
    H2 signUpHeader = new H2("Sign Up");
    private TextField name = new TextField("Full Name (e.g. John Doe)");
    private EmailField email = new EmailField("Email (e.g. name@example.com)");
    private PasswordField password = new PasswordField("Password (Min. 8 characters w/ at least 1 letter & number)");
    private TextField id = new TextField("ID (SA ID Number)");
    private Button signUpButton = new Button("Sign Up");
    private FormLayout formLayout = new FormLayout(name, email, password, id);
    private TextField emailLogIn = new TextField("Email");
    private PasswordField passwordLogIn = new PasswordField("Password");
    private Button loginButton = new Button("Log In");
    private FormLayout loginFormLayout = new FormLayout(emailLogIn, passwordLogIn);
    private OrderedList voteContainer = new OrderedList();
    Button logoutButton = new Button("Sign Out");
    FirebaseService firebaseService = new FirebaseService();
    PasswordService passwordService = new PasswordService();

    public VoteView() throws InterruptedException, ExecutionException {
        constructUI();
        if (VaadinSession.getCurrent().getAttribute(user.class) != null) {
            if(firebaseService.userVoted(VaadinSession.getCurrent().getAttribute(user.class))) {
                H3 header = new H3("You have already voted!");
                header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
                add(header);
                add(logoutButton);
            } else {
                addVotingInterface();
         
            }
        } else {
            addSignUp();
            addLogin(); 
            
        }
        logoutButton.addClickListener(click -> {
            VaadinSession.getCurrent().setAttribute(user.class, null);
            getUI().ifPresent(ui -> ui.navigate("home"));
        });
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
                                firebaseService.insertUser(name.getValue(), email.getValue(), id.getValue(), passwordService.hashPassword(password.getValue()));
                                //VaadinSession.getCurrent().setAttribute(user.class, firebaseService.getUser(email.getValue()));
                                removeLogin();
                                removeSignUp();
                                Notification.show("User has been created successfully");
                                getUI().ifPresent(ui -> ui.navigate("home"));
                            }
                        }
                    } else {
                        Notification.show("Email is disposable");
                    }
                } catch (Exception e) {
                    //To do: Decide how the error will be handled
                    e.printStackTrace();
                }                
            }
        }); 
        loginButton.addClickListener(click -> {
            if(emailLogIn.isEmpty() || passwordLogIn.isEmpty()) {
                Notification.show("Fill in all the fields");
            } else {
                try {
                    if(firebaseService.userExistsUsingEmail(emailLogIn.getValue())) {
                        if(passwordService.checkPassword(passwordLogIn.getValue(), firebaseService.getUser(emailLogIn.getValue()).getHash())) {
                            VaadinSession.getCurrent().setAttribute(user.class, firebaseService.getUser(emailLogIn.getValue()));
                            removeLogin();
                            removeSignUp();
                            addVotingInterface();
                        } else {
                            Notification.show("Incorrect password");
                        }
                    } else {
                        Notification.show("User does not exist");
                    }
                } catch (Exception e) {
                    //To do: Decide how the error will be handled
                    e.printStackTrace();
                }
            }
        });
    }
    public void addSignUp() {
        signUpButton.setWidthFull();
        formLayout.setWidth("100%");
        name.setPattern("^([a-zA-z,/.-]+)\\s([a-zA-z,/.-]+)$");
        name.setRequiredIndicatorVisible(true);
        email.setRequiredIndicatorVisible(true);
        password.setPattern("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        password.setRequiredIndicatorVisible(true);
        id.setPattern("^[0-9]{13}$");
        id.setRequiredIndicatorVisible(true);
        if (VaadinSession.getCurrent().getAttribute(user.class) != null) {
            removeSignUp();
        } else {
            add(signUpHeader, formLayout);
            add(signUpButton);
        }    
    }
    public void addLogin() {
        loginButton.setWidthFull();
        loginFormLayout.setWidth("100%");
        if (VaadinSession.getCurrent().getAttribute(user.class) != null) {
            removeLogin();
        } else {
            add(loginHeader, loginFormLayout);
            add(loginButton);
        }
    }
    public void addVotingInterface() throws InterruptedException, ExecutionException {

        if(firebaseService.userVoted(VaadinSession.getCurrent().getAttribute(user.class))) {
            H3 header = new H3("You have already voted!");
            header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
            add(header);

            add(logoutButton);

        } else {
            List<candidate> candidates = firebaseService.getCandidates();
            for (candidate candidate : candidates) {
                    add(new VoteViewCard(candidate));
            }
            add(logoutButton);
        }
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
    private void constructUI() {
        addClassNames("home-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        
        voteContainer.addClassNames(Gap.LARGE, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer);
        add(container, voteContainer);
    }
}
