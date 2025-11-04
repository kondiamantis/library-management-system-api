import dayjs from '../lib/dayjs';
import { DashboardData } from '../types/dashboard';

const today = dayjs();

const monthsBack = (count: number) =>
  Array.from({ length: count }).map((_, index) => today.subtract(count - index - 1, 'month'));

export const getMockDashboardData = (): DashboardData => {
  const recentMonths = monthsBack(6);

  return {
    metrics: {
      catalogueSize: 12875,
      physicalCopies: 31642,
      itemsOnLoan: 2480,
      overdueItems: 148,
      activeMembers: 4096,
      newMembersThisMonth: 128,
      pendingReservations: 342,
      digitalEngagementRate: 64
    },
    circulationTrends: recentMonths.map((month, idx) => ({
      month: month.format('MMM YYYY'),
      loans: 1800 + idx * 120,
      reservations: 900 + idx * 80,
      digitalLoans: 720 + idx * 65
    })),
    genreDistribution: [
      { genre: 'Fiction', checkouts: 3560 },
      { genre: 'Mystery & Thriller', checkouts: 2690 },
      { genre: 'Non-Fiction', checkouts: 2280 },
      { genre: 'Science & Technology', checkouts: 1875 },
      { genre: "Children's", checkouts: 1620 },
      { genre: 'Young Adult', checkouts: 1388 }
    ],
    topBorrowers: [
      { id: '1', name: 'Ava Thompson', loansThisQuarter: 34, overdueCount: 0 },
      { id: '2', name: 'William Chen', loansThisQuarter: 29, overdueCount: 1 },
      { id: '3', name: 'Nia Rodriguez', loansThisQuarter: 25, overdueCount: 0 }
    ],
    dueSoonItems: [
      {
        id: 'due-1',
        title: 'Project Hail Mary',
        memberName: 'James Patel',
        dueDate: today.add(2, 'day').toISOString(),
        daysRemaining: 2
      },
      {
        id: 'due-2',
        title: 'Thinking, Fast and Slow',
        memberName: 'Eloise Parks',
        dueDate: today.add(3, 'day').toISOString(),
        daysRemaining: 3
      },
      {
        id: 'due-3',
        title: 'Data Feminism',
        memberName: 'Jordan White',
        dueDate: today.add(5, 'day').toISOString(),
        daysRemaining: 5
      }
    ],
    staffTasks: [
      {
        id: 'task-1',
        title: 'Curate New Arrivals display',
        description: 'Feature the weekly shipments and highlight award-winning titles.',
        dueDate: today.add(1, 'day').toISOString(),
        priority: 'high',
        isCompleted: false
      },
      {
        id: 'task-2',
        title: 'Inventory audit – Children’s section',
        description: 'Confirm physical counts for picture books and graphic novels.',
        dueDate: today.add(4, 'day').toISOString(),
        priority: 'medium',
        isCompleted: false
      },
      {
        id: 'task-3',
        title: 'Schedule story time volunteers',
        description: 'Finalize the December roster and email confirmations.',
        dueDate: today.add(7, 'day').toISOString(),
        priority: 'low',
        isCompleted: true
      }
    ],
    notifications: [
      {
        id: 'notif-1',
        message: '12 laptops reserved for the STEM workshop this Saturday.',
        severity: 'info',
        timestamp: today.subtract(2, 'hour').toISOString()
      },
      {
        id: 'notif-2',
        message: '5 memberships require address verification.',
        severity: 'warning',
        timestamp: today.subtract(6, 'hour').toISOString()
      },
      {
        id: 'notif-3',
        message: 'Self-checkout kiosk #2 reported an RFID read error.',
        severity: 'critical',
        timestamp: today.subtract(13, 'hour').toISOString()
      }
    ],
    recentActivity: [
      {
        id: 'activity-1',
        memberName: 'Kai Johnson',
        action: 'Checkout',
        resource: 'The Midnight Library',
        timestamp: today.subtract(23, 'minute').toISOString()
      },
      {
        id: 'activity-2',
        memberName: 'Sophia Nguyen',
        action: 'Return',
        resource: 'Designing Data-Intensive Applications',
        timestamp: today.subtract(1, 'hour').toISOString()
      },
      {
        id: 'activity-3',
        memberName: 'Omar Ali',
        action: 'Reservation',
        resource: 'The Anthropocene Reviewed',
        timestamp: today.subtract(3, 'hour').toISOString()
      },
      {
        id: 'activity-4',
        memberName: 'Amelia Brown',
        action: 'Fine Paid',
        resource: 'Overdue fee cleared',
        timestamp: today.subtract(9, 'hour').toISOString()
      }
    ]
  };
};
