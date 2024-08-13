import { test, expect } from '@playwright/test';
import { doUserRegistration } from '../test-utils/test-utils';

// test registration flow

test.beforeEach(async ({ page }) => {
  await page.goto('localhost:3000');
});  

test('test registration through login button', async ({ page }) => {
  
  // wait on catalog items first, as a normal user would
  await expect(async () => {
      const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
      await expect(catalogItems.data.length != 0).toBeTruthy();
  }).toPass({
      timeout: 30_000 // TODO: fix this - should be 5_000
  });

  // shouldn't see 'cart' or 'orders' buttons
  await expect(page.getByTestId('cart-button')).not.toBeVisible();
  await expect(page.getByTestId('orders-button')).not.toBeVisible();

  await doUserRegistration({ page });

  // should be back to home page and should see catalog items
  await expect(page.getByTestId('catalog-items-container')).toBeVisible();

  // should see 'Log Out' button now
  await expect(page.getByTestId('login-logout-button')).toContainText(/Log Out/);

  // should see 'cart' and 'orders' buttons now
  await expect(page.getByTestId('cart-button')).toBeVisible();
  await expect(page.getByTestId('orders-button')).toBeVisible();
  
});