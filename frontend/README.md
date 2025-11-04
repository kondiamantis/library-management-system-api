# Library Management Dashboard UI

This package contains the operations dashboard UI for the Library Management System. It is a React + TypeScript single-page application powered by Vite and Tailwind CSS. The dashboard is designed to surface circulation insights, member engagement, staff tasks, and system notifications in one command-center view.

## Quick start

```bash
cd frontend
npm install

# copy the env template if you plan to call the backend
cp .env.example .env.local   # edit VITE_API_BASE_URL as needed

npm run dev
```

The dev server starts on `http://localhost:5173` by default. It proxies dashboard data from the backend when `VITE_API_BASE_URL` is provided; otherwise, it falls back to enriched mock data for local exploration.

## Available scripts

- `npm run dev` – start the Vite dev server with hot module reload
- `npm run build` – generate a production build (`dist/`) using TypeScript project references
- `npm run preview` – serve the production build locally
- `npm run lint` – run ESLint with the project&#39;s flat config (TypeScript + React rules)

## Integration with the backend API

- Set `VITE_API_BASE_URL` to the root of your API (e.g. `http://localhost:8080/api`). The dashboard will request `GET {VITE_API_BASE_URL}/dashboard`.
- The hook `src/hooks/useDashboardData.ts` encapsulates the API call. If the endpoint is unreachable or unset, it automatically falls back to `src/data/mockDashboardData.ts` so the UI remains functional.
- Align the backend response with the `DashboardData` contract defined in `src/types/dashboard.ts`. Each of the dashboard widgets binds directly to those shapes (metrics, circulation trends, tasks, notifications, and activity feed).
- When the API is ready, remove or adjust the mock data helper, and any new fields will be type-checked across the component tree.

## Project structure

```
frontend/
├── src/
│   ├── components/
│   │   ├── common/Panel.tsx          # shared chrome for surface sections
│   │   └── dashboard/                # dashboard widgets (metrics, charts, lists)
│   ├── data/mockDashboardData.ts     # rich sample payload used as fallback data
│   ├── hooks/useDashboardData.ts     # data-fetch hook with mock fallback & refresh
│   ├── layouts/DashboardLayout.tsx   # global shell (header, footer, refresh control)
│   ├── lib/dayjs.ts                  # day.js configuration (relative time, formats)
│   ├── pages/DashboardPage.tsx       # orchestration of dashboard widgets
│   ├── App.tsx / main.tsx            # app entry points
│   └── types/dashboard.ts            # strongly-typed dashboard contract
├── index.html                        # Vite HTML entry point
├── tailwind.config.js                # Tailwind theme extensions (brand palette)
└── eslint.config.js                  # Flat ESLint config (JS, TS, React, Prettier)
```

## Extending the dashboard

- **Wire up live data:** implement `/dashboard` in the Spring Boot API to return the `DashboardData` shape. Include totals, trend series, and collections for due items, staff tasks, and notifications.
- **Add filtering:** layer in library branch, collection, or time-range filters. The `useDashboardData` hook already exposes a `refresh` function for refetching.
- **Deploy:** run `npm run build` and serve the generated `dist/` folder behind the Spring Boot static resources (`src/main/resources/static`) or your preferred static host.

For questions or follow-up enhancements, open an issue alongside this branch in the main repository.
