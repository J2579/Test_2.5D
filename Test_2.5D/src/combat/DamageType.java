package combat;

public enum DamageType {

	PHYSICAL {
		public String toString() {
			return "Physical";
		}
	},
	
	MAGICAL {
		public String toString() {
			return "Magical";
		}
	},

	PURE {
		public String toString() {
			return "Pure";
		}
	},
}
