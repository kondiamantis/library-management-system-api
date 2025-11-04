import { ReactNode } from 'react';

interface PanelProps {
  title: string;
  subtitle?: string;
  action?: ReactNode;
  children: ReactNode;
  className?: string;
}

const Panel = ({ title, subtitle, action, children, className = '' }: PanelProps) => (
  <section
    className={`flex flex-col gap-5 rounded-3xl border border-white/5 bg-slate-900/50 p-6 shadow-xl shadow-black/30 backdrop-blur-sm ${className}`}
  >
    <header className="flex flex-wrap items-start justify-between gap-3">
      <div>
        <h2 className="text-lg font-semibold text-white">{title}</h2>
        {subtitle ? <p className="mt-1 text-sm text-slate-400">{subtitle}</p> : null}
      </div>
      {action ? <div className="text-sm text-slate-300">{action}</div> : null}
    </header>
    <div className="flex-1">{children}</div>
  </section>
);

export default Panel;
