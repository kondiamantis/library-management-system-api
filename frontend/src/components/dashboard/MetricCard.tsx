interface MetricCardProps {
  label: string;
  value: string;
  deltaLabel: string;
  deltaValue: number;
  deltaIsPositive?: boolean;
  accentColor?: string;
}

const MetricCard = ({
  label,
  value,
  deltaLabel,
  deltaValue,
  deltaIsPositive = true,
  accentColor = 'from-brand-500/90 to-brand-400/70'
}: MetricCardProps) => (
  <article className="flex flex-col gap-4 rounded-3xl border border-white/5 bg-gradient-to-br from-slate-900/60 to-slate-900/30 p-5 text-sm shadow-lg shadow-black/40">
    <div className={`inline-flex w-max items-center gap-2 rounded-full bg-gradient-to-r ${accentColor} px-3 py-1 text-xs font-semibold uppercase tracking-widest text-white/90`}> 
      <span className="h-1.5 w-1.5 rounded-full bg-white" />
      {label}
    </div>
    <div>
      <p className="text-3xl font-semibold text-white">{value}</p>
    </div>
    <div className="mt-auto flex items-center justify-between text-xs text-slate-300">
      <span className="text-slate-400">{deltaLabel}</span>
      <span
        className={`font-semibold ${deltaIsPositive ? 'text-emerald-400' : 'text-rose-400'}`}
      >
        {deltaIsPositive ? '+' : '-'}
        {Math.abs(deltaValue)}%
      </span>
    </div>
  </article>
);

export default MetricCard;
