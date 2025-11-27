package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class NotesPage extends BasePage {
    private final By heading = By.cssSelector("h1, [data-test='notes-heading']");
    private final By composeNoteBtn = By.cssSelector("[data-test='compose-note'], button:contains('Compose note')");
    private final By openLibraryBtn = By.cssSelector("[data-test='open-library'], button:contains('Open my library')");
    private final By sortDropdown = By.cssSelector("[data-test='sort-dropdown']");
    private final By viewToggle = By.cssSelector("[data-test='view-toggle']");
    private final By noteTypeFilter = By.cssSelector("[data-test='note-type-filter']");
    private final By focusTagsArea = By.cssSelector("[data-test='focus-tags'], .tags-area");
    private final By trendingTopics = By.cssSelector("[data-test='trending-topics'], .trending-topics");

    public NotesPage open() { driver.get(ConfigManager.getBaseUrl() + "/notes"); return this; }
    public boolean isHeadingVisible() { return isVisible(heading); }
    public boolean isComposeNoteVisible() { return isVisible(composeNoteBtn); }
    public boolean isOpenLibraryVisible() { return isVisible(openLibraryBtn); }
    public boolean isSortDropdownVisible() { return isVisible(sortDropdown); }
    public boolean isViewToggleVisible() { return isVisible(viewToggle); }
    public boolean isNoteTypeFilterVisible() { return isVisible(noteTypeFilter); }
    public boolean isFocusTagsAreaVisible() { return isVisible(focusTagsArea); }
    public boolean isTrendingTopicsVisible() { return isVisible(trendingTopics); }

    // checks whether a note with the given title appears on the page
    public boolean isNotePresent(String title) {
        try {
            return isVisible(By.xpath("//h3[contains(text(),'" + title.replace("'","\\'") + "') or contains(.,'" + title.replace("'","\\'") + "') ]"));
        } catch (Exception e) {
            return false;
        }
    }
    public void clickComposeNote() { click(composeNoteBtn); }
    public void clickTag(String tagName) { click(By.xpath("//span[contains(@class,'tag') and contains(text(),'" + tagName + "')]" ) ); }
}
