// @ts-check
const eslint = require('@eslint/js');
const typescript = require('typescript-eslint');
const angular = require('angular-eslint');

module.exports = typescript.config(
  {
    files: ['**/*.ts'],
    extends: [
      eslint.configs.recommended,
      ...typescript.configs.recommended,
      ...typescript.configs.stylistic,
      ...angular.configs.tsRecommended,
    ],
    processor: angular.processInlineTemplates,
    rules: {
      '@angular-eslint/directive-selector': [
        'error',
        {
          type: 'attribute',
          prefix: 'app',
          style: 'camelCase',
        },
      ],
      '@angular-eslint/component-selector': [
        'error',
        {
          type: 'element',
          prefix: 'app',
          style: 'kebab-case',
        },
      ],
      '@/indent': ['error', 2],
      '@/comma-dangle': 'off',
      '@/dot-notation': 'off',
      '@/no-implied-eval': 'off',
      '@/naming-convention': 'off',
      '@/object-curly-spacing': 'off',
      '@typescript-eslint/array-type': 'error',
      '@typescript-eslint/no-explicit-any': 'error',
      '@/quotes': ['error', 'single', {'allowTemplateLiterals': true}],
      'no-console': 'off',
      'no-extra-parens': ['error', 'all'],
      'array-callback-return': 'error',
      'curly': 'error',
      'default-case': 'warn',
      'eqeqeq': ['error', 'always'],
      'guard-for-in': 'warn',
      'no-caller': 'error',
      'no-empty-function': ['error', {'allow': ['constructors'] }],
      'no-eval': 'error',
      'no-extra-bind': 'error',
      'no-floating-decimal': 'error',
      'no-lone-blocks': 'error',
      'no-multi-spaces': 'error',
      'no-new': 'warn',
      'no-return-assign': 'error',
      'no-self-compare': 'error',
      'no-useless-call': 'error',
      'no-undef-init': 'error',
      'block-spacing': 'error',
      'brace-style': 'error',
      'comma-dangle': ['error', 'never'],
      'func-call-spacing': ['error', 'never'],
      'max-len': ['error', {'code': 120, 'ignoreComments': true}],
      'new-cap': ['error', {'newIsCap': false, 'capIsNew': false}],
      'new-parens': 'error',
      'no-nested-ternary': 'error',
      'no-unneeded-ternary': 'error',
      'quotes': ['warn', 'single', {
        'avoidEscape': true,
        'allowTemplateLiterals': true
      }],
      'arrow-spacing': ['error', {'before': true, 'after': true}],
      'no-useless-computed-key': 'error',
      'no-useless-constructor': 'off',
      'no-prototype-builtins': 'off',
      'no-var': 'warn',
      'no-magic-numbers': ['warn', {'ignore': [0, 1]}],
      'import/named': 'off',
      'import/no-unresolved': 'off'
    }
  },
  {
    files: ['**/*.html'],
    extends: [
      ...angular.configs.templateRecommended,
      ...angular.configs.templateAccessibility,
    ],
    rules: {},
  }
);
