import { DashboardMetrics } from '../../types/dashboard';

interface EngagementHighlightsProps {
  metrics: DashboardMetrics;
}

const formatNumber = new Intl.NumberFormat('en-US');

const EngagementHighlights = ({ metrics }: EngagementHighlightsProps) => (
  <section className="flex flex-col gap-4 rounded-3xl border border-white/5 bg-slate-900/40 p-5 shadow-xl">
    <header>
      <p className="text-sm font-semibold uppercase tracking-wide text-brand-200">
        Member activity
      </p>
      <h3 className="text-xl font-semibold text-white">Engagement pulse</h3>
    </header>
    <div className="grid gap-4 sm:grid-cols-2">
      <div className="rounded-2xl border border-brand-500/20 bg-brand-500/10 p-4">
        <p className="text-xs uppercase tracking-wide text-brand-200">Active members</p>
        <p className="mt-2 text-2xl font-semibold text-white">
          {formatNumber.format(metrics.activeMembers)}
        </p>
        <p className="text-xs text-brand-100">+3.2% vs last month</p>
      </div>
      <div className="rounded-2xl border border-emerald-500/20 bg-emerald-500/10 p-4">
        <p className="text-xs uppercase tracking-wide text-emerald-200">New memberships</p>
        <p className="mt-2 text-2xl font-semibold text-white">{metrics.newMembersThisMonth}</p>
        <p className="text-xs text-emerald-100">Welcome kits pending: 8</p>
      </div>
      <div className="rounded-2xl border border-amber-500/20 bg-amber-500/10 p-4">
        <p className="text-xs uppercase tracking-wide text-amber-200">Reservations</p>
        <p className="mt-2 text-2xl font-semibold text-white">{metrics.pendingReservations}</p>
        <p className="text-xs text-amber-100">63% pickup within 24h</p>
      </div>
      <div className="rounded-2xl border border-fuchsia-500/20 bg-fuchsia-500/10 p-4">
        <p className="text-xs uppercase tracking-wide text-fuchsia-200">Digital engagement</p>
        <div className="mt-2 flex items-baseline gap-2 text-white">
          <p className="text-2xl font-semibold">{metrics.digitalEngagementRate}%</p>
          <span className="text-xs text-fuchsia-100">of active members</span>
        </div>
        <p className="text-xs text-fuchsia-100">+5.6 pts vs last quarter</p>
      </div>
    </div>
  </section>
);

export default EngagementHighlights;
