import MetricCard from './MetricCard';
import { DashboardMetrics } from '../../types/dashboard';

interface MetricsGridProps {
  metrics: DashboardMetrics;
}

const numberFormatter = new Intl.NumberFormat('en-US');

const MetricsGrid = ({ metrics }: MetricsGridProps) => (
  <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
    <MetricCard
      label="Catalogue Size"
      value={numberFormatter.format(metrics.catalogueSize)}
      deltaLabel="Titles indexed in the last 30 days"
      deltaValue={6.4}
      accentColor="from-brand-500/90 to-emerald-400/70"
    />
    <MetricCard
      label="Physical Copies"
      value={numberFormatter.format(metrics.physicalCopies)}
      deltaLabel="New copies processed this week"
      deltaValue={4.8}
      accentColor="from-brand-500/80 to-blue-400/70"
    />
    <MetricCard
      label="Items On Loan"
      value={numberFormatter.format(metrics.itemsOnLoan)}
      deltaLabel="Change vs. previous week"
      deltaValue={3.1}
      accentColor="from-purple-500/80 to-fuchsia-400/70"
    />
    <MetricCard
      label="Overdue Items"
      value={numberFormatter.format(metrics.overdueItems)}
      deltaLabel="Week-over-week change"
      deltaValue={2.2}
      deltaIsPositive={false}
      accentColor="from-rose-500/80 to-orange-400/70"
    />
  </div>
);

export default MetricsGrid;
