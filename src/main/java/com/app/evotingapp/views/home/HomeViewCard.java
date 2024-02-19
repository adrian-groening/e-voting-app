package com.app.evotingapp.views.home;

import java.util.concurrent.ExecutionException;

import com.app.evotingapp.Entities.candidate;
import com.app.evotingapp.services.FirebaseService;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
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

public class HomeViewCard extends ListItem {

    FirebaseService firebaseService = new FirebaseService();

    public HomeViewCard(candidate candidate) {
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

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        try {
            badge.setText("Poll: " + (((firebaseService.getVotes(candidate)*100)/firebaseService.getVoteCount())) +"%");
        } catch (InterruptedException e) {
            //To do: Decide how the error will be handled
            e.printStackTrace();
        } catch (ExecutionException e) {
            //To do: Decide how the error will be handled
            e.printStackTrace();
        }
        add(header, subtitle, description, badge);
    }
}
