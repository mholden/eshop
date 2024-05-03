import { test, expect } from '@playwright/test';

// test basic things on the homepage

test.beforeEach(async ({ page }) => {
  await page.goto('localhost:3000');
});  

// title should contain 'EShop'
test('test title contains eshop', async ({ page }) => {
  await expect(page).toHaveTitle(/EShop/);
});

// make sure catalogItems load
test('test catalog items load', async ({ page }) => {
  await expect(async () => {
    const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
    await expect(catalogItems.data.length != 0).toBeTruthy();
  }).toPass({
    timeout: 5_000
  });
});

// check for log in button, make sure clicking it behaves as expected
test('test log in button works', async ({ page }) => {
  // wait on catalog items first, as a normal user would
  await expect(async () => {
    const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
    await expect(catalogItems.data.length != 0).toBeTruthy();
  }).toPass({
    timeout: 5_000
  });
  await page.getByTestId('login-logout-button').click();
  await page.locator('id=username').fill("alice@testeshop.ca");
});

// test clicking a catalog item, make sure it behaves as expected
test('test clicking catalog item', async ({ page }) => {
  // wait on catalog items first, as a normal user would
  await expect(async () => {
    const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
    await expect(catalogItems.data.length != 0).toBeTruthy();
  }).toPass({
    timeout: 5_000
  });
  await page.getByTestId('cat-item-0-link').click();
  await page.getByTestId('add-to-cart-button').click();
  await page.locator('id=username').fill("alice@testeshop.ca");
});

// check for home button, make sure clicking it behaves as expected
test('test clicking home button', async ({ page }) => {
  // wait on catalog items first, as a normal user would
  await expect(async () => {
    const catalogItems = await page.evaluate(() => window.store.getState().catalogItems);
    await expect(catalogItems.data.length != 0).toBeTruthy();
  }).toPass({
    timeout: 5_000
  });
  await page.getByTestId('cat-item-0-link').click();
  await page.getByTestId('home-button').click();
  await page.getByTestId('cat-item-1-link').click();
  await page.getByTestId('home-button').click();
  await page.getByTestId('login-logout-button').click();
  await page.locator('id=username').fill("alice@testeshop.ca");
});
