package com.app.evotingapp.views.vote;

import java.util.concurrent.ExecutionException;

import com.app.evotingapp.Entities.candidate;
import com.app.evotingapp.Entities.user;
import com.app.evotingapp.Entities.vote;
import com.app.evotingapp.services.FirebaseService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

public class VoteViewCard extends ListItem {

    FirebaseService firebaseService = new FirebaseService();

    public VoteViewCard(candidate candidate) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(candidate.getName());

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(candidate.getParty());

        Paragraph description = new Paragraph(candidate.getBio());
        description.addClassName(Margin.Vertical.MEDIUM);

        Button voteButton = new Button("Vote");
        voteButton.addClassName("vote-button");
        voteButton.addClickListener(e -> {
            user currentUser = VaadinSession.getCurrent().getAttribute(user.class);            
            vote vote = new vote(candidate, currentUser);
            try {
                if(firebaseService.userVoted(currentUser)) {
                    Notification.show("You have already voted!");
                } else {
                    firebaseService.makeVote(vote);
                    Notification.show("Vote casted!");
                    UI.getCurrent().navigate("home");
                }
            } catch (InterruptedException e1) {
                //To do: Decide how the error will be handled
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                //To do: Decide how the error will be handled
                e1.printStackTrace();
            }
        });
        add(header, subtitle, description, voteButton);
    }
}

