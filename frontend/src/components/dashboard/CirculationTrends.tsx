import {
  Area,
  AreaChart,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis
} from 'recharts';
import Panel from '../common/Panel';
import { TrendPoint } from '../../types/dashboard';

interface CirculationTrendsProps {
  data: TrendPoint[];
}

const CirculationTrends = ({ data }: CirculationTrendsProps) => (
  <Panel
    title="Circulation & Reservations"
    subtitle="Rolling 6-month performance snapshot"
    action={<span className="text-xs text-slate-400">Loans vs. digital utilisation</span>}
    className="col-span-1 xl:col-span-2"
  >
    <div className="h-72">
      <ResponsiveContainer width="100%" height="100%">
        <AreaChart data={data} margin={{ left: 0, top: 20, right: 20 }}>
          <defs>
            <linearGradient id="loansGradient" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="rgba(99, 102, 241, 0.85)" stopOpacity={1} />
              <stop offset="95%" stopColor="rgba(99, 102, 241, 0.05)" stopOpacity={0} />
            </linearGradient>
            <linearGradient id="digitalGradient" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="rgba(16, 185, 129, 0.85)" stopOpacity={1} />
              <stop offset="95%" stopColor="rgba(16, 185, 129, 0.05)" stopOpacity={0} />
            </linearGradient>
            <linearGradient id="reservationGradient" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="rgba(249, 115, 22, 0.85)" stopOpacity={1} />
              <stop offset="95%" stopColor="rgba(249, 115, 22, 0.05)" stopOpacity={0} />
            </linearGradient>
          </defs>
          <CartesianGrid strokeDasharray="4" stroke="rgba(148, 163, 184, 0.2)" />
          <XAxis dataKey="month" stroke="rgba(203, 213, 225, 0.6)" tickLine={false} />
          <YAxis
            stroke="rgba(203, 213, 225, 0.6)"
            tickLine={false}
            tickFormatter={(value) => `${value}`}
          />
          <Tooltip
            contentStyle={{
              backgroundColor: 'rgba(15, 23, 42, 0.85)',
              borderRadius: '16px',
              border: '1px solid rgba(59,130,246,0.35)',
              color: '#e2e8f0'
            }}
          />
          <Area
            type="monotone"
            dataKey="loans"
            stroke="rgba(99, 102, 241, 1)"
            strokeWidth={2.5}
            fillOpacity={1}
            fill="url(#loansGradient)"
            name="Physical loans"
          />
          <Area
            type="monotone"
            dataKey="digitalLoans"
            stroke="rgba(16, 185, 129, 1)"
            strokeWidth={2.5}
            fillOpacity={1}
            fill="url(#digitalGradient)"
            name="Digital loans"
          />
          <Area
            type="monotone"
            dataKey="reservations"
            stroke="rgba(249, 115, 22, 1)"
            strokeWidth={2.5}
            fillOpacity={1}
            fill="url(#reservationGradient)"
            name="Reservations"
          />
        </AreaChart>
      </ResponsiveContainer>
    </div>
  </Panel>
);

export default CirculationTrends;
