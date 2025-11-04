import dayjs from '../../lib/dayjs';
import Panel from '../common/Panel';
import { DueSoonItem } from '../../types/dashboard';

interface DueSoonListProps {
  items: DueSoonItem[];
}

const DueSoonList = ({ items }: DueSoonListProps) => (
  <Panel
    title="Due Soon"
    subtitle="Prioritize follow-ups within the next week"
    action={<span className="text-xs text-slate-400">Auto-reminders enabled</span>}
  >
    <ul className="space-y-3 text-sm">
      {items.map((item) => (
        <li
          key={item.id}
          className="flex items-start justify-between rounded-2xl border border-white/5 bg-white/5 px-4 py-3"
        >
          <div>
            <p className="font-semibold text-white">{item.title}</p>
            <p className="text-xs text-slate-400">{item.memberName}</p>
          </div>
          <div className="text-right text-xs">
            <p
              className={`font-semibold ${
                item.daysRemaining <= 2 ? 'text-rose-300' : 'text-amber-200'
              }`}
            >
              Due {dayjs(item.dueDate).format('MMM D')}
            </p>
            <p className="text-slate-400">{item.daysRemaining} days</p>
          </div>
        </li>
      ))}
    </ul>
  </Panel>
);

export default DueSoonList;
