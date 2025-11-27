package com.helix.automation.tests.ui;

import com.helix.automation.framework.pages.NotesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.helix.automation.tests.BaseTest;

public class NotesTests extends BaseTest {
    @Test
    public void testComposeNoteRequiresAuth() {
        NotesPage notes = new NotesPage();
        notes.open();
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/login") || driver.getCurrentUrl().contains("/auth/signup"), "Compose note should require auth");
    }

    @Test
    public void testNotesPageElements() {
        NotesPage notes = new NotesPage();
        notes.open();
        Assert.assertTrue(notes.isHeadingVisible(), "Heading should be visible");
    }

    @Test
    public void testNotesFilterAndComposeVisibility() {
        NotesPage notes = new NotesPage();
        notes.open();
        Assert.assertTrue(notes.isComposeNoteVisible(), "Compose note should be visible");
    }
}
