function Loading({ message = "Loading..." }) {
  return (
    <div
      className="flex items-center justify-center gap-3 rounded-xl bg-white p-6 text-green-800 shadow-sm"
      role="status"
    >
      <span className="h-5 w-5 animate-spin rounded-full border-2 border-green-200 border-t-green-700" />
      <span>{message}</span>
    </div>
  );
}

export default Loading;
