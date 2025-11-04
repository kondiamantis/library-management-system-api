import js from '@eslint/js';
import globals from 'globals';
import tseslint from '@typescript-eslint/eslint-plugin';
import tsParser from '@typescript-eslint/parser';
import reactPlugin from 'eslint-plugin-react';
import eslintConfigPrettier from 'eslint-config-prettier';

const tsRecommended = tseslint.configs.recommended;
const normalizedTsRecommended = Array.isArray(tsRecommended)
  ? tsRecommended
  : [tsRecommended];

export default [
  {
    ignores: ['dist/**', 'build/**', 'node_modules/**']
  },
  js.configs.recommended,
  ...normalizedTsRecommended,
  reactPlugin.configs.recommended,
  eslintConfigPrettier,
  {
    files: ['**/*.{ts,tsx,js,jsx}'],
    languageOptions: {
      parser: tsParser,
      parserOptions: {
        ecmaVersion: 'latest',
        sourceType: 'module',
        ecmaFeatures: {
          jsx: true
        }
      },
      globals: {
        ...globals.browser
      }
    },
    plugins: {
      '@typescript-eslint': tseslint,
      react: reactPlugin
    },
    settings: {
      react: {
        version: 'detect'
      }
    },
    rules: {
      'react/react-in-jsx-scope': 'off',
      'react/prop-types': 'off'
    }
  }
];
