note: currently, backend should have ESHOP_EXTERNAL_DNS_NAME_OR_IP set to 
docker.for.mac.localhost in its .env file for this frontend to work

cd frontend && npm run start
cd frontend && npx playwright test


more testing notes:

to run a test in UI mode so that you can see what it's doing:
SLOMO=300 npx playwright test registration.spec.js --project chromium --ui

or 'headed':
SLOMO=300 npx playwright test order.spec.js -g 'test placing an order' --project chromium --headed