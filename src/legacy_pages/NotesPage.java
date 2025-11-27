package com.passthenote.pages.legacy;

// NOTE: legacy page object kept for reference during refactor. Use com.passthenote.framework.pages.NotesPage instead.

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NotesPage extends BasePage {
    private final By heading = By.cssSelector("h1, [data-test='notes-heading']");
    private final By composeNoteBtn = By.cssSelector("[data-test='compose-note'], button:contains('Compose note')");
    private final By openLibraryBtn = By.cssSelector("[data-test='open-library'], button:contains('Open my library')");
    private final By sortDropdown = By.cssSelector("[data-test='sort-dropdown']");
    private final By viewToggle = By.cssSelector("[data-test='view-toggle']");
    private final By noteTypeFilter = By.cssSelector("[data-test='note-type-filter']");
    private final By focusTagsArea = By.cssSelector("[data-test='focus-tags'], .tags-area");
    private final By trendingTopics = By.cssSelector("[data-test='trending-topics'], .trending-topics");
    private final By tag = By.cssSelector(".tag, [data-test^='tag-']");

    public NotesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.passthenote.com/notes");
    }

    public boolean isHeadingVisible() {
        return isVisible(heading);
    }

    public boolean isComposeNoteVisible() {
        return isVisible(composeNoteBtn);
    }

    public boolean isOpenLibraryVisible() {
        return isVisible(openLibraryBtn);
    }

    public boolean isSortDropdownVisible() {
        return isVisible(sortDropdown);
    }

    public boolean isViewToggleVisible() {
        return isVisible(viewToggle);
    }

    public boolean isNoteTypeFilterVisible() {
        return isVisible(noteTypeFilter);
    }

    public boolean isFocusTagsAreaVisible() {
        return isVisible(focusTagsArea);
    }

    public boolean isTrendingTopicsVisible() {
        return isVisible(trendingTopics);
    }

    public void clickComposeNote() {
        click(composeNoteBtn);
    }

    public void clickTag(String tagName) {
        click(By.xpath("//span[contains(@class,'tag') and contains(text(),'" + tagName + "')]") );
    }
}
