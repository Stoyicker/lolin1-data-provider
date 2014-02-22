package org.lolin1.models;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.util.ajax.JSON;

public class Champion {

	private class ActiveSpell extends PassiveSpell {
		/**
		 * Passive spells are not considered by Riot to have a cooldown nor a
		 * range
		 */
		private String cooldown, range;

		public ActiveSpell(JSONObject jsonObject) {
			// TODO Create the ActiveSpell object
		}
	}

	private class PassiveSpell {

		protected String imageName, detail, name;

		@SuppressWarnings("unchecked")
		private PassiveSpell(HashMap<String, Object> passiveDescriptor) {
			this.detail = (String) passiveDescriptor.get("description");
			this.name = (String) passiveDescriptor.get("name");
			this.imageName = ((HashMap<String, String>) passiveDescriptor
					.get("image")).get("full");
		}

		private PassiveSpell(String _name, String _detail, String _imageName) {
			this.name = _name;
			this.detail = _detail;
			this.imageName = _imageName;
		}

		@Override
		public String toString() {
			StringBuilder ret = new StringBuilder("{");
			Field[] fields = this.getClass().getFields();
			for (int i = 0; i < fields.length;) {
				Field x = fields[i];
				x.setAccessible(Boolean.TRUE);
				try {
					ret.append("\"" + x.getName() + "\":\"" + x.get(this)
							+ "\"");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace(System.err);
				}
				if (++i < fields.length) {
					ret.append(",");
				}
			}
			ret.append("}");
			return ret.toString();
		}
	}

	private String key, name, title, attackrange, mpperlevel, mp, attackdamage,
			hp, hpperlevel, attackdamageperlevel, armor, mpregenperlevel,
			hpregen, critperlevel, spellblockperlevel, mpregen,
			attackspeedperlevel, spellblock, movespeed, attackspeedoffset,
			crit, hpregenperlevel, armorperlevel, lore;
	private final String[] tags;
	private final ActiveSpell[] spells;
	private final PassiveSpell passive;

	@SuppressWarnings("unchecked")
	public Champion(String descriptor) {
		HashMap<String, Object> parsedDescriptor = (HashMap<String, Object>) JSON
				.parse(descriptor);

		Field[] fields = this.getClass().getDeclaredFields();
		for (Field x : fields) {
			if (x.getType() == String.class) {
				x.setAccessible(Boolean.TRUE);
				try {
					x.set(this, parsedDescriptor.get(x.getName()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		String _tags = JSON.toString(parsedDescriptor.get("tags"))
				.replace("[", "").replace("]", "").replace("\"", "\"");
		StringTokenizer tagsTokenizer = new StringTokenizer(_tags, ",");
		this.tags = new String[tagsTokenizer.countTokens()];
		for (int i = 0; i < this.tags.length; i++) {
			this.tags[i] = tagsTokenizer.nextToken();
		}
		this.passive = new PassiveSpell(
				(HashMap<String, Object>) parsedDescriptor.get("passive"));
		String _parsedSpells = JSON.toString(parsedDescriptor.get("spells"));
		JSONArray spellsArray = null;
		try {
			spellsArray = new JSONArray(_parsedSpells);
		} catch (JSONException e) {
			e.printStackTrace(System.err);
		}
		this.spells = new ActiveSpell[spellsArray.length()];
		for (int i = 0; i < this.spells.length; i++) {
			try {
				this.spells[i] = new ActiveSpell(spellsArray.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace(System.err);
			}
		}
		System.exit(-1);
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("{");
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length;) {
			Field x = fields[i];
			if (!x.getName().contentEquals("tags")
					&& !x.getName().contentEquals("spells")) {
				x.setAccessible(Boolean.TRUE);
				try {
					ret.append("\"" + x.getName() + "\":\"" + x.get(this)
							+ "\"");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace(System.err);
				}
			}
			if (++i < fields.length) {
				ret.append(",");
			}
		}
		ret.append("\"tags\":[");
		for (int i = 0; i < this.tags.length;) {
			ret.append("\"" + this.tags[i] + "\"");
			if (++i < this.tags.length) {
				ret.append(",");
			}
		}
		ret.append("],");
		ret.append(this.passive.toString()).append(",");
		for (int i = 0; i < this.spells.length;) {
			ret.append(this.spells[i].toString());
			if (++i < this.tags.length) {
				ret.append(",");
			}
		}
		return ret.append("}").toString();
	}
}
