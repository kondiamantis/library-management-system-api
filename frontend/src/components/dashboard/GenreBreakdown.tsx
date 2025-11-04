import { Bar, BarChart, Cell, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';
import Panel from '../common/Panel';
import { GenreDistribution } from '../../types/dashboard';

interface GenreBreakdownProps {
  data: GenreDistribution[];
}

const GenreBreakdown = ({ data }: GenreBreakdownProps) => (
  <Panel
    title="Genre Circulation"
    subtitle="Top circulating categories"
    action={<span className="text-xs text-slate-500">Past 30 days</span>}
  >
    <div className="h-64">
      <ResponsiveContainer width="100%" height="100%">
        <BarChart data={data} layout="vertical" margin={{ left: 0, top: 12, right: 16 }}>
          <XAxis type="number" hide />
          <YAxis
            type="category"
            dataKey="genre"
            width={150}
            tick={{ fill: 'rgba(226,232,240,0.85)', fontSize: 12 }}
          />
          <Tooltip
            cursor={{ fill: 'rgba(59, 130, 246, 0.1)' }}
            contentStyle={{
              background: 'rgba(2, 6, 23, 0.9)',
              borderRadius: '14px',
              border: '1px solid rgba(59,130,246,0.35)',
              color: '#e2e8f0'
            }}
          />
          <Bar dataKey="checkouts" radius={[12, 12, 12, 12]} fill="rgba(59, 130, 246, 0.9)">
            {data.map((_entry, index) => (
              <Cell
                key={`cell-${index}`}
                fill={`rgba(${Math.max(44, 59 - index * 6)}, ${130 - index * 8}, 246, 0.85)`}
              />
            ))}
          </Bar>
        </BarChart>
      </ResponsiveContainer>
    </div>
  </Panel>
);

export default GenreBreakdown;
