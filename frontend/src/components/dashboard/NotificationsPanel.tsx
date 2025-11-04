import dayjs from '../../lib/dayjs';
import Panel from '../common/Panel';
import { NotificationItem } from '../../types/dashboard';

interface NotificationsPanelProps {
  notifications: NotificationItem[];
}

const severityBadge: Record<NotificationItem['severity'], string> = {
  info: 'bg-brand-500/20 text-brand-200',
  warning: 'bg-amber-500/20 text-amber-200',
  critical: 'bg-rose-600/20 text-rose-200'
};

const NotificationsPanel = ({ notifications }: NotificationsPanelProps) => (
  <Panel title="System Notifications" subtitle="Automated alerts from operations stack">
    <ul className="space-y-3 text-sm">
      {notifications.map((notification) => (
        <li
          key={notification.id}
          className="flex items-start justify-between rounded-2xl border border-white/5 bg-white/5 px-4 py-3"
        >
          <div className="pr-4">
            <span
              className={`mb-2 inline-flex items-center rounded-full px-3 py-1 text-[11px] font-semibold uppercase tracking-wider ${severityBadge[notification.severity]}`}
            >
              {notification.severity}
            </span>
            <p className="text-slate-200">{notification.message}</p>
          </div>
          <p className="text-xs text-slate-500">
            {dayjs(notification.timestamp).format('MMM D, h:mm A')}
          </p>
        </li>
      ))}
    </ul>
  </Panel>
);

export default NotificationsPanel;
