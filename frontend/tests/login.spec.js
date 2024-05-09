import { test, expect } from '@playwright/test';

// test login flow

test.beforeEach(async ({ page }) => {
  await page.goto('localhost:3000');
});  

test('test login through login button', async ({ page }) => {
  
  // wait on catalog items first, as a normal user would
  await expect(async () => {
    const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
    await expect(catalogItems.data.length != 0).toBeTruthy();
  }).toPass({
    timeout: 5_000
  });
  
  // shouldn't see 'cart' or 'orders' buttons
  await expect(page.getByTestId('cart-button')).not.toBeVisible();
  await expect(page.getByTestId('orders-button')).not.toBeVisible();

  const loginButton = await page.getByTestId('login-logout-button');
  await expect(loginButton).toContainText(/Log In/);
  await loginButton.click();
  await page.locator('id=username').fill("alice@testeshop.ca");
  await page.locator('id=password').fill("alice");
  await page.locator('id=kc-login').click();

  // should be back to home page and should see catalog items
  await expect(page.getByTestId('catalog-items-container')).toBeVisible();

  // should see 'Log Out' button now
  await expect(page.getByTestId('login-logout-button')).toContainText(/Log Out/);

  // should see 'cart' and 'orders' buttons now
  await expect(page.getByTestId('cart-button')).toBeVisible();
  await expect(page.getByTestId('orders-button')).toBeVisible();

});

test('test login through add-to-cart button', async ({ page }) => {
  
    // wait on catalog items first, as a normal user would
    await expect(async () => {
      const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
      await expect(catalogItems.data.length != 0).toBeTruthy();
    }).toPass({
      timeout: 5_000
    });
    
    // shouldn't see 'cart' or 'orders' buttons
    await expect(page.getByTestId('cart-button')).not.toBeVisible();
    await expect(page.getByTestId('orders-button')).not.toBeVisible();
  
    // should see log in button
    await expect(page.getByTestId('login-logout-button')).toContainText(/Log In/);

    await page.getByTestId('cat-item-0-link').click();
    await page.getByTestId('add-to-cart-button').click(); // should trigger redirect to log in
    await page.locator('id=username').fill("alice@testeshop.ca");
    await page.locator('id=password').fill("alice");
    await page.locator('id=kc-login').click();
    
    // should be back to catalog item page
    await expect(page).toHaveURL(/.*catalog\/item\?/);
  
    // should see 'Log Out' button now
    await expect(page.getByTestId('login-logout-button')).toContainText(/Log Out/);
  
    // should see 'cart' and 'orders' buttons now
    await expect(page.getByTestId('cart-button')).toBeVisible();
    await expect(page.getByTestId('orders-button')).toBeVisible();
  
    // clicking add-to-cart again should not redirect to log in
    await page.getByTestId('add-to-cart-button').click();
    await expect(page).toHaveURL(/.*catalog\/item\?/);
});

test('test logout', async ({ page }) => {
  
    // wait on catalog items first, as a normal user would
    await expect(async () => {
      const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
      await expect(catalogItems.data.length != 0).toBeTruthy();
    }).toPass({
      timeout: 5_000
    });
    
    // shouldn't see 'cart' or 'orders' buttons
    await expect(page.getByTestId('cart-button')).not.toBeVisible();
    await expect(page.getByTestId('orders-button')).not.toBeVisible();
  
    const loginButton = await page.getByTestId('login-logout-button');
    await expect(loginButton).toContainText(/Log In/);
    await loginButton.click();
    await page.locator('id=username').fill("alice@testeshop.ca");
    await page.locator('id=password').fill("alice");
    await page.locator('id=kc-login').click();
  
    // should be back to home page and should see catalog items
    await expect(page.getByTestId('catalog-items-container')).toBeVisible();

    // should see 'cart' and 'orders' buttons now
    await expect(page.getByTestId('cart-button')).toBeVisible();
    await expect(page.getByTestId('orders-button')).toBeVisible();
  
    // should see 'Log Out' button now
    const logoutButton = await page.getByTestId('login-logout-button');
    await expect(logoutButton).toContainText(/Log Out/);
    await logoutButton.click();
  
   // should be back to home page and should see catalog items
   await expect(page.getByTestId('catalog-items-container')).toBeVisible();

   // shouldn't see these anymore
   await expect(page.getByTestId('cart-button')).not.toBeVisible();
   await expect(page.getByTestId('orders-button')).not.toBeVisible();
  
  });