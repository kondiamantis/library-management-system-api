import { useCallback, useEffect, useMemo, useState } from 'react';
import { getMockDashboardData } from '../data/mockDashboardData';
import { DashboardData } from '../types/dashboard';

interface UseDashboardDataResult {
  data: DashboardData | null;
  isLoading: boolean;
  lastUpdatedAt: Date | null;
  refresh: () => void;
}

export const useDashboardData = (): UseDashboardDataResult => {
  const [data, setData] = useState<DashboardData | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [lastUpdatedAt, setLastUpdatedAt] = useState<Date | null>(null);

  const apiBaseUrl = import.meta.env.VITE_API_BASE_URL as string | undefined;

  const loadData = useCallback(async () => {
    setIsLoading(true);

    try {
      if (apiBaseUrl) {
        const response = await fetch(`${apiBaseUrl.replace(/\/$/, '')}/dashboard`);

        if (!response.ok) {
          throw new Error(`Failed to load dashboard data: ${response.status}`);
        }

        const payload = (await response.json()) as DashboardData;
        setData(payload);
      } else {
        setData(getMockDashboardData());
      }
    } catch (error) {
      // eslint-disable-next-line no-console
      console.warn('Dashboard API unavailable, using mock data.', error);
      setData(getMockDashboardData());
    } finally {
      setLastUpdatedAt(new Date());
      setIsLoading(false);
    }
  }, [apiBaseUrl]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const refresh = useCallback(() => {
    loadData();
  }, [loadData]);

  return useMemo(
    () => ({
      data,
      isLoading,
      lastUpdatedAt,
      refresh
    }),
    [data, isLoading, lastUpdatedAt, refresh]
  );
};
