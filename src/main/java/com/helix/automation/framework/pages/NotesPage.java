package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class NotesPage extends BasePage {
    // Helper to try multiple locator strategies in order: id, name, css, xpath
    private WebElement findElement(String id, String name, String css, String xpath) {
        try { return driver.findElement(By.id(id)); } catch (Exception ignored) {}
        try { return driver.findElement(By.name(name)); } catch (Exception ignored) {}
        try { return driver.findElement(By.cssSelector(css)); } catch (Exception ignored) {}
        try { return driver.findElement(By.xpath(xpath)); } catch (Exception ignored) {}
        throw new NoSuchElementException("Element not found: " + id + ", " + name + ", " + css + ", " + xpath);
    }

    public NotesPage open() { driver.get(ConfigManager.getBaseUrl() + "/notes"); return this; }

    public boolean isHeadingVisible() {
        try {
            WebElement el = findElement("notes-heading", "", "h1, [data-test='notes-heading']", "//h1[contains(.,'Notes')]");
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isComposeNoteVisible() {
        try {
            WebElement el = findElement("compose-note", "", "[data-test='compose-note'], button:contains('Compose note')", "//button[contains(.,'Compose note')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isOpenLibraryVisible() {
        try {
            WebElement el = findElement("open-library", "", "[data-test='open-library'], button:contains('Open my library')", "//button[contains(.,'Open my library')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isSortDropdownVisible() {
        try {
            WebElement el = findElement("sort-dropdown", "", "[data-test='sort-dropdown']", "//select[contains(@class,'sort')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isViewToggleVisible() {
        try {
            WebElement el = findElement("view-toggle", "", "[data-test='view-toggle']", "//button[contains(@class,'view-toggle')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isNoteTypeFilterVisible() {
        try {
            WebElement el = findElement("note-type-filter", "", "[data-test='note-type-filter']", "//div[contains(@class,'note-type-filter')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isFocusTagsAreaVisible() {
        try {
            WebElement el = findElement("focus-tags", "", "[data-test='focus-tags'], .tags-area", "//div[contains(@class,'tags-area')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isTrendingTopicsVisible() {
        try {
            WebElement el = findElement("trending-topics", "", "[data-test='trending-topics'], .trending-topics", "//div[contains(@class,'trending-topics')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    // checks whether a note with the given title appears on the page
    public boolean isNotePresent(String title) {
        try {
            WebElement el = findElement("", "", "", "//h3[contains(text(),'" + title.replace("'","\\'") + "') or contains(.,'" + title.replace("'","\\'") + "') ]");
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public void clickComposeNote() {
        WebElement el = findElement("compose-note", "", "[data-test='compose-note'], button:contains('Compose note')", "//button[contains(.,'Compose note')]" );
        el.click();
    }

    public void clickTag(String tagName) {
        WebElement el = findElement("", "", "", "//span[contains(@class,'tag') and contains(text(),'" + tagName + "')]" );
        el.click();
    }
}
