import dayjs from '../../lib/dayjs';
import Panel from '../common/Panel';
import { StaffTask } from '../../types/dashboard';

interface StaffTasksProps {
  tasks: StaffTask[];
}

const priorityBadgeMap: Record<StaffTask['priority'], string> = {
  high: 'bg-rose-500/15 text-rose-200',
  medium: 'bg-amber-500/15 text-amber-200',
  low: 'bg-emerald-500/15 text-emerald-200'
};

const StaffTasks = ({ tasks }: StaffTasksProps) => (
  <Panel title="Staff Tasks" subtitle="Team operations checklist">
    <ul className="space-y-3 text-sm">
      {tasks.map((task) => (
        <li
          key={task.id}
          className={`flex items-start justify-between rounded-2xl border border-white/5 px-4 py-3 ${
            task.isCompleted ? 'bg-emerald-500/10' : 'bg-white/5'
          }`}
        >
          <div className="pr-4">
            <div className="flex items-center gap-2">
              <p className="font-semibold text-white">{task.title}</p>
              <span
                className={`rounded-full px-2.5 py-0.5 text-[11px] font-semibold uppercase tracking-wide ${priorityBadgeMap[task.priority]}`}
              >
                {task.priority}
              </span>
            </div>
            <p className="mt-1 text-xs text-slate-400">{task.description}</p>
          </div>
          <div className="flex flex-col items-end text-xs text-slate-300">
            <span>{dayjs(task.dueDate).format('MMM D')}</span>
            <span className={task.isCompleted ? 'text-emerald-300' : 'text-slate-500'}>
              {task.isCompleted ? 'Completed' : 'In progress'}
            </span>
          </div>
        </li>
      ))}
    </ul>
  </Panel>
);

export default StaffTasks;
