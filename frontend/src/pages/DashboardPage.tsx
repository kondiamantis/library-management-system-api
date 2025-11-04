import MetricsGrid from '../components/dashboard/MetricsGrid';
import CirculationTrends from '../components/dashboard/CirculationTrends';
import GenreBreakdown from '../components/dashboard/GenreBreakdown';
import EngagementHighlights from '../components/dashboard/EngagementHighlights';
import TopBorrowers from '../components/dashboard/TopBorrowers';
import DueSoonList from '../components/dashboard/DueSoonList';
import StaffTasks from '../components/dashboard/StaffTasks';
import NotificationsPanel from '../components/dashboard/NotificationsPanel';
import ActivityFeed from '../components/dashboard/ActivityFeed';
import { DashboardData } from '../types/dashboard';

interface DashboardPageProps {
  data: DashboardData;
  isLoading: boolean;
}

const DashboardPage = ({ data, isLoading }: DashboardPageProps) => (
  <div className="flex flex-col gap-6">
    <MetricsGrid metrics={data.metrics} />

    <div className="grid gap-6 xl:grid-cols-3">
      <CirculationTrends data={data.circulationTrends} />
      <EngagementHighlights metrics={data.metrics} />
    </div>

    <div className="grid gap-6 lg:grid-cols-2">
      <GenreBreakdown data={data.genreDistribution} />
      <TopBorrowers borrowers={data.topBorrowers} />
    </div>

    <div className="grid gap-6 xl:grid-cols-3">
      <DueSoonList items={data.dueSoonItems} />
      <StaffTasks tasks={data.staffTasks} />
      <NotificationsPanel notifications={data.notifications} />
    </div>

    <ActivityFeed activity={data.recentActivity} />

    {isLoading ? (
      <div className="mt-4 flex justify-center text-xs uppercase tracking-wide text-slate-400">
        <span className="animate-pulse">Refreshing data...</span>
      </div>
    ) : null}
  </div>
);

export default DashboardPage;
