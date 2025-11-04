import { ReactNode } from 'react';
import { useDashboardData } from './hooks/useDashboardData';
import DashboardLayout from './layouts/DashboardLayout';
import DashboardPage from './pages/DashboardPage';

const App = () => {
  const { data, isLoading, lastUpdatedAt, refresh } = useDashboardData();

  const renderContent = (): ReactNode => {
    if (isLoading && !data) {
      return (
        <div className="flex h-full flex-1 items-center justify-center">
          <div className="flex flex-col items-center gap-3 text-slate-300">
            <span className="h-12 w-12 animate-spin rounded-full border-4 border-brand-500 border-t-transparent" />
            <p className="text-sm font-medium uppercase tracking-wide text-slate-400">
              Loading dashboard metrics...
            </p>
          </div>
        </div>
      );
    }

    if (!data) {
      return (
        <div className="flex flex-1 flex-col items-center justify-center gap-3">
          <p className="text-base font-semibold text-white">No dashboard data available</p>
          <button
            type="button"
            className="rounded-md bg-brand-500 px-5 py-2 text-sm font-semibold text-white shadow-lg shadow-brand-500/30 transition hover:bg-brand-400"
            onClick={refresh}
          >
            Retry
          </button>
        </div>
      );
    }

    return <DashboardPage data={data} isLoading={isLoading} />;
  };

  return (
    <DashboardLayout
      onRefresh={refresh}
      lastUpdatedAt={lastUpdatedAt}
      isLoading={isLoading}
    >
      {renderContent()}
    </DashboardLayout>
  );
};

export default App;
