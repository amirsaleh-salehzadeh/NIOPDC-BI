package aip.common;
	public class AIPException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static final int AIPEX_UNKNOWN=0;
		public static final int AIPEX_DELETE=1;
		public static final int AIPEX_SAVE=2;
		public static final int AIPEX_SAVE_DUPLICATE=3;
		
		private int type=0;
		
		public AIPException(String message) {
			// TODO Auto-generated constructor stub
			super(message);
		}
		public AIPException(String message,Throwable cause) {
			// TODO Auto-generated constructor stub
			super(message,cause);
		}
		public AIPException(Throwable cause) {
			// TODO Auto-generated constructor stub
			super(cause);
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}

	}