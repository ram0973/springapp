export default function FormExtra() {
  return(
    <div className="flex">
      <input id="remember-me" name="remember-me" type="checkbox"
             className="text-purple-600 focus:ring-purple-500 border-gray-300 rounded"/>
      <label htmlFor="remember-me" className="ml-4 text-sm text-gray-900">Remember me</label>
      <div className="text-sm ml-4">
        <a href="#" className="font-medium text-purple-600 hover:text-purple-500">Forgot your password?</a>
      </div>
    </div>
  )
}