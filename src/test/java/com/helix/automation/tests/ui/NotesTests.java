package com.helix.automation.tests.ui;

import com.helix.automation.framework.pages.NotesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.helix.automation.tests.BaseTest;

public class NotesTests extends BaseTest {
    @org.testng.annotations.BeforeClass
    public void setupClass() {
        login();
    }

    @Test
    public void testComposeNoteVisibleWhenLoggedIn() {
        NotesPage notes = new NotesPage();
        notes.open();
        // BaseTest auto-logs in, so compose should be visible for an authenticated user
        Assert.assertTrue(notes.isComposeNoteVisible(), "Compose note should be visible for authenticated users");
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
