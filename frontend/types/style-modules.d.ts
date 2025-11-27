// File: `frontend/types/style-modules.d.ts`
declare module '*.module.css' {
    const classes: Record<string, string>;
    export default classes;
}
