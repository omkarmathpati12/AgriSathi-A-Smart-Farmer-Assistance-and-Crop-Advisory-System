function ErrorMessage({ message, onRetry }) {
  if (!message) {
    return null;
  }

  return (
    <div
      role="alert"
      className="flex flex-wrap items-center justify-between gap-3 rounded-xl border border-red-200 bg-red-50 p-4 text-sm text-red-800"
    >
      <span>{message}</span>
      {onRetry && (
        <button
          type="button"
          onClick={onRetry}
          className="font-semibold underline"
        >
          Try again
        </button>
      )}
    </div>
  );
}

export default ErrorMessage;
