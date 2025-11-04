import dayjs from '../../lib/dayjs';
import Panel from '../common/Panel';
import { ActivityItem } from '../../types/dashboard';

interface ActivityFeedProps {
  activity: ActivityItem[];
}

const actionColor: Record<ActivityItem['action'], string> = {
  Checkout: 'bg-emerald-500/15 text-emerald-200',
  Return: 'bg-slate-500/20 text-slate-200',
  Reservation: 'bg-brand-500/20 text-brand-200',
  'Fine Paid': 'bg-amber-500/20 text-amber-200',
  'New Membership': 'bg-purple-500/20 text-purple-200'
};

const ActivityFeed = ({ activity }: ActivityFeedProps) => (
  <Panel title="Recent Activity" subtitle="Live feed of member interactions">
    <ul className="space-y-4 text-sm">
      {activity.map((entry) => (
        <li key={entry.id} className="flex items-start justify-between gap-4">
          <div className="flex flex-col gap-1">
            <div className="flex items-center gap-2">
              <span className="font-semibold text-white">{entry.memberName}</span>
              <span
                className={`rounded-full px-2 py-0.5 text-[11px] font-semibold uppercase tracking-wide ${actionColor[entry.action]}`}
              >
                {entry.action}
              </span>
            </div>
            <p className="text-xs text-slate-400">{entry.resource}</p>
          </div>
          <span className="text-xs text-slate-500">
            {dayjs(entry.timestamp).fromNow()}
          </span>
        </li>
      ))}
    </ul>
  </Panel>
);

export default ActivityFeed;
