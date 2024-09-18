TODO: update this note re. BackEndServiceLocations env

cd frontend && npm run start
cd frontend && npx playwright test


more testing notes:

to run a test in UI mode so that you can see what it's doing:
SLOMO=300 npx playwright test registration.spec.js --project chromium --ui

or 'headed':
SLOMO=300 npx playwright test order.spec.js -g 'test placing an order' --project chromium --headed