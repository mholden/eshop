import { expect } from '@playwright/test';
import { randomUUID } from 'crypto';

// register a new test user
export const doUserRegistration = async ({ page }) => {
  const loginButton = await page.getByTestId('login-logout-button');
  await expect(loginButton).toContainText(/Log In/);
  await loginButton.click();

  await expect(page).toHaveURL(/.*openid-connect\/auth.*/);

  const userEmail = "testuser-" + randomUUID().split("-")[4] + "@testeshop.ca";
  await page.getByText('Register').click();
  await page.locator('id=firstName').fill("Test");  
  await page.locator('id=lastName').fill("User");
  await page.locator('id=email').fill(userEmail);
  await page.locator('id=password').fill("Pass@word1");
  await page.locator('id=password-confirm').fill("Pass@word1");
  await page.getByRole('button', { name: 'Register' }).click();
}