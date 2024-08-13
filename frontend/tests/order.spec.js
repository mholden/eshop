import { test, expect } from '@playwright/test';
import { doUserRegistration } from '../test-utils/test-utils';

// test order work flows

test.beforeEach(async ({ page }) => {
  await page.goto('localhost:3000');
}); 

test('test placing an order', async ({ page }) => {
  
    // wait on catalog items
    await expect(async () => {
        const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
        await expect(catalogItems.data.length != 0).toBeTruthy();
    }).toPass({
        timeout: 30_000 // TODO: fix this - should be 5_000
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

    await expect(page.getByTestId('cart-checkout-confirmation-modal')).not.toBeVisible();

    // checkout
    await page.getByTestId('cart-checkout-button').click();
    await expect(page.getByTestId('cart-checkout-confirmation-modal')).toBeVisible();

    await page.getByTestId('view-my-order-button').click();

    // this should be 0 again
    await expect(page.getByTestId('topbar-cart-number')).toContainText("0");

    const ordersTable = await page.getByTestId('orders-table');
    await expect(ordersTable).toBeVisible();
    await expect(ordersTable.locator('tbody').locator('tr')).toHaveCount(1);

    await expect(page.getByTestId('orders-table-item-0-state')).toContainText("PAYMENT_SUCCEEDED");
});