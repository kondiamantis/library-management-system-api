export interface DashboardMetrics {
  catalogueSize: number;
  physicalCopies: number;
  itemsOnLoan: number;
  overdueItems: number;
  activeMembers: number;
  newMembersThisMonth: number;
  pendingReservations: number;
  digitalEngagementRate: number;
}

export interface TrendPoint {
  month: string;
  loans: number;
  reservations: number;
  digitalLoans: number;
}

export interface GenreDistribution {
  genre: string;
  checkouts: number;
}

export interface ActivityItem {
  id: string;
  memberName: string;
  action: 'Checkout' | 'Return' | 'Reservation' | 'Fine Paid' | 'New Membership';
  resource: string;
  timestamp: string;
}

export interface DueSoonItem {
  id: string;
  title: string;
  memberName: string;
  dueDate: string;
  daysRemaining: number;
}

export interface StaffTask {
  id: string;
  title: string;
  description: string;
  dueDate: string;
  priority: 'low' | 'medium' | 'high';
  isCompleted: boolean;
}

export interface NotificationItem {
  id: string;
  message: string;
  severity: 'info' | 'warning' | 'critical';
  timestamp: string;
}

export interface TopBorrower {
  id: string;
  name: string;
  loansThisQuarter: number;
  overdueCount: number;
}

export interface DashboardData {
  metrics: DashboardMetrics;
  circulationTrends: TrendPoint[];
  genreDistribution: GenreDistribution[];
  topBorrowers: TopBorrower[];
  dueSoonItems: DueSoonItem[];
  staffTasks: StaffTask[];
  notifications: NotificationItem[];
  recentActivity: ActivityItem[];
}
