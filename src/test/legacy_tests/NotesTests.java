package com.helix.automation.tests.legacy;

import com.helix.automation.framework.pages.NotesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NotesTests extends BaseTest {

    @Test
    public void testNotesLandingPageFiltersAreVisible() {
        NotesPage notes = new NotesPage();
        notes.open();
        Assert.assertTrue(notes.isHeadingVisible(), "Notes heading should be visible");
        Assert.assertTrue(notes.isComposeNoteVisible(), "Compose note button should be visible");
        Assert.assertTrue(notes.isOpenLibraryVisible(), "Open my library button should be visible");
        Assert.assertTrue(notes.isSortDropdownVisible(), "Sort dropdown should be visible");
        Assert.assertTrue(notes.isViewToggleVisible(), "View mode toggle should be visible");
        Assert.assertTrue(notes.isNoteTypeFilterVisible(), "Note type filter should be visible");
        Assert.assertTrue(notes.isFocusTagsAreaVisible(), "Focus tags area should be visible");
        Assert.assertTrue(notes.isTrendingTopicsVisible(), "Trending topics should be visible");
    }

    @Test
    public void testAnonymousUserComposeNoteRequiresAuth() {
        NotesPage notes = new NotesPage();
        notes.open();
        notes.clickComposeNote();
        Assert.assertTrue(driver.getCurrentUrl().contains("login") || driver.getPageSource().toLowerCase().contains("sign in"), "Compose note should require authentication");
    }

    @Test
    public void testTagFilterInteraction() {
        NotesPage notes = new NotesPage();
        notes.open();
        notes.clickTag("#testing");
        Assert.assertTrue(notes.isFocusTagsAreaVisible(), "Tag filter should update UI");
    }
}
