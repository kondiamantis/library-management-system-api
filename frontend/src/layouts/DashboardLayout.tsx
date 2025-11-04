import { ReactNode } from 'react';
import dayjs from '../lib/dayjs';

interface DashboardLayoutProps {
  children: ReactNode;
  onRefresh: () => void;
  lastUpdatedAt: Date | null;
  isLoading: boolean;
}

const navigation = [
  { label: 'Dashboard', isActive: true },
  { label: 'Collections', isActive: false },
  { label: 'Members', isActive: false },
  { label: 'Circulation', isActive: false },
  { label: 'Reports', isActive: false }
];

const DashboardLayout = ({
  children,
  onRefresh,
  lastUpdatedAt,
  isLoading
}: DashboardLayoutProps) => (
  <div className="min-h-screen bg-slate-950/95 text-slate-100">
    <div className="mx-auto flex min-h-screen max-w-[1280px] flex-col px-6 py-8">
      <header className="mb-8 flex flex-col gap-6 rounded-3xl border border-white/5 bg-slate-900/40 p-6 shadow-2xl shadow-brand-900/20 backdrop-blur">
        <div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-widest text-brand-300">
              Library Management System
            </p>
            <h1 className="mt-1 text-3xl font-bold text-white md:text-4xl">
              Operations Command Center
            </h1>
            <p className="mt-2 max-w-xl text-sm text-slate-300">
              Real-time insight into circulation, member engagement, and upcoming priorities for
              your library team.
            </p>
          </div>
          <div className="flex items-center gap-3">
            <button
              type="button"
              onClick={onRefresh}
              className="inline-flex items-center gap-2 rounded-xl border border-brand-500/60 bg-brand-500/10 px-4 py-2 text-sm font-semibold text-brand-200 transition hover:bg-brand-500/20"
              disabled={isLoading}
            >
              <span
                className={`h-3 w-3 rounded-full ${
                  isLoading ? 'animate-pulse bg-brand-200/70' : 'bg-emerald-400'
                }`}
              />
              {isLoading ? 'Refreshing' : 'Refresh data'}
            </button>
            <div className="rounded-xl border border-white/5 bg-white/5 px-4 py-2 text-xs font-medium text-slate-200">
              {lastUpdatedAt
                ? `Updated ${dayjs(lastUpdatedAt).fromNow()}`
                : 'Waiting for first sync...'}
            </div>
          </div>
        </div>
        <nav className="flex flex-wrap gap-2 text-xs font-medium">
          {navigation.map(({ label, isActive }) => (
            <span
              key={label}
              className={`rounded-full px-3 py-1 ${
                isActive
                  ? 'bg-brand-500 text-white shadow shadow-brand-900/40'
                  : 'bg-white/5 text-slate-300'
              }`}
            >
              {label}
            </span>
          ))}
        </nav>
      </header>

      <main className="flex flex-1 flex-col gap-6 pb-16">{children}</main>

      <footer className="mt-auto flex items-center justify-between border-t border-white/5 pt-6 text-xs text-slate-500">
        <span>© {new Date().getFullYear()} City Central Library</span>
        <span>Version 0.1.0 Dashboard Prototype</span>
      </footer>
    </div>
  </div>
);

export default DashboardLayout;
