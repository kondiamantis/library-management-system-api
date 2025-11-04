import Panel from '../common/Panel';
import { TopBorrower } from '../../types/dashboard';

interface TopBorrowersProps {
  borrowers: TopBorrower[];
}

const TopBorrowers = ({ borrowers }: TopBorrowersProps) => (
  <Panel title="Top Borrowers" subtitle="Quarter-to-date engagement">
    <div className="overflow-hidden rounded-2xl border border-white/5">
      <table className="min-w-full divide-y divide-white/5 text-sm">
        <thead className="bg-white/5 text-xs uppercase tracking-wider text-slate-300">
          <tr>
            <th scope="col" className="px-4 py-3 text-left font-medium">
              Patron
            </th>
            <th scope="col" className="px-4 py-3 text-center font-medium">
              Loans
            </th>
            <th scope="col" className="px-4 py-3 text-center font-medium">
              Overdue incidents
            </th>
            <th scope="col" className="px-4 py-3 text-right font-medium">
              Status
            </th>
          </tr>
        </thead>
        <tbody className="divide-y divide-white/5 text-slate-200">
          {borrowers.map((borrower) => (
            <tr key={borrower.id} className="transition hover:bg-white/5">
              <td className="px-4 py-3">
                <div className="font-semibold text-white">{borrower.name}</div>
                <p className="text-xs text-slate-400">Frequent visitor</p>
              </td>
              <td className="px-4 py-3 text-center font-semibold text-brand-200">
                {borrower.loansThisQuarter}
              </td>
              <td className="px-4 py-3 text-center">
                <span
                  className={`inline-flex items-center justify-center rounded-full px-3 py-1 text-xs font-semibold ${
                    borrower.overdueCount === 0
                      ? 'bg-emerald-500/10 text-emerald-300'
                      : 'bg-amber-500/10 text-amber-300'
                  }`}
                >
                  {borrower.overdueCount}
                </span>
              </td>
              <td className="px-4 py-3 text-right text-xs text-slate-400">
                {borrower.overdueCount === 0 ? 'In good standing' : 'Monitor account'}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  </Panel>
);

export default TopBorrowers;
