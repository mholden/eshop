import { test, expect } from '@playwright/test';
import { doUserRegistration } from '../test-utils/test-utils';

// test basket work flows

test.beforeEach(async ({ page }) => {
  await page.goto('localhost:3000');
});  

test('test adding to basket', async ({ page }) => {
  
    // wait on catalog items
    await expect(async () => {
        const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
        await expect(catalogItems.data.length != 0).toBeTruthy();
    }).toPass({
        timeout: 5_000
    });
  
    await doUserRegistration({ page });

    await expect(page.getByTestId('topbar-cart-number')).toContainText("0");

    const catItemName = await page.getByTestId('cat-item-3-name').textContent();
    await page.getByTestId('cat-item-3-link').click(); 
    await page.getByTestId('add-to-cart-button').click();

    // number on cart icon should now be 1
    await expect(page.getByTestId('topbar-cart-number')).toContainText("1");

    await page.getByTestId('cart-button').click();

    const cartTable = await page.getByTestId('cart-table');
    await expect(cartTable).toBeVisible();

    // cart table should have 1 entry
    await expect(cartTable.locator('tbody').locator('tr')).toHaveCount(1);
    await expect(page.getByTestId('cart-table-item-0-name')).toContainText(catItemName);

});

test.skip('test removing from basket', async ({ page }) => { // TODO: need to implement remove-single-cart-item 
  
    // wait on catalog items
    await expect(async () => {
        const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
        await expect(catalogItems.data.length != 0).toBeTruthy();
    }).toPass({
        timeout: 5_000
    });
  
    await doUserRegistration({ page });

    await expect(page.getByTestId('topbar-cart-number')).toContainText("0");

    const catItemName = await page.getByTestId('cat-item-1-name').textContent();
    await page.getByTestId('cat-item-1-link').click(); 
    await page.getByTestId('add-to-cart-button').click();

    // number on cart icon should now be 1
    await expect(page.getByTestId('topbar-cart-number')).toContainText("1");

    await page.getByTestId('cart-button').click();

    const cartTable = await page.getByTestId('cart-table');
    await expect(cartTable).toBeVisible();

    // cart table should have 1 entry
    await expect(cartTable.locator('tbody').locator('tr')).toHaveCount(1);
    await expect(page.getByTestId('cart-table-item-0-name')).toContainText(catItemName);

    // now remove it
    await page.getByTestId('cart-table-item-0-remove-button').click();
    await expect(page.getByTestId('topbar-cart-number')).toContainText("0");

    // TODO: cart table should be invisible now
});

test('test remove all from basket', async ({ page }) => {
  
    // wait on catalog items
    await expect(async () => {
        const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
        await expect(catalogItems.data.length != 0).toBeTruthy();
    }).toPass({
        timeout: 5_000
    });
  
    await doUserRegistration({ page });

    await expect(page.getByTestId('topbar-cart-number')).toContainText("0");

    const catItemName = await page.getByTestId('cat-item-1-name').textContent();
    await page.getByTestId('cat-item-1-link').click(); 
    await page.getByTestId('add-to-cart-button').click();

    // number on cart icon should now be 1
    await expect(page.getByTestId('topbar-cart-number')).toContainText("1");

    await page.getByTestId('cart-button').click();

    const cartTable = await page.getByTestId('cart-table');
    await expect(cartTable).toBeVisible();

    // cart table should have 1 entry
    await expect(cartTable.locator('tbody').locator('tr')).toHaveCount(1);
    await expect(page.getByTestId('cart-table-item-0-name')).toContainText(catItemName);

    // now remove it
    await page.getByTestId('cart-table-remove-all-button').click();
    await expect(page.getByTestId('topbar-cart-number')).toContainText("0");

    // TODO: cart table should be invisible now
});