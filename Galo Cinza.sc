SCRIPT_START
{
	LVAR_INT scplayer iGymLS iGymSF iGymLV iGaloCinza iEstilo

	GET_PLAYER_CHAR 0 scplayer
	
	iEstilo = 5

	main_loop:
	WAIT 0
	SET_CHAR_ANIM_SPEED scplayer "FightD_M" 1.67 //now it's not a caress
	IF IS_CHAR_FIGHTING scplayer
	AND iGaloCinza = FALSE
		IF iGymLS = FALSE
			CLEO_CALL ReadGlobalVar 0 (8153)(iGymLS) // gym_ls_defeated
		ELSE
			CLEO_CALL WriteGlobalVar 0 (8156 1)() // player_been_taught_moveLS
		ENDIF
		IF iGymSF = FALSE
			CLEO_CALL ReadGlobalVar 0 (8154)(iGymSF) // gym_sf_defeated
		ELSE
			CLEO_CALL WriteGlobalVar 0 (8155 1)() // player_been_taught_moveSF
		ENDIF
		IF iGymLV = FALSE
			CLEO_CALL ReadGlobalVar 0 (8158)(iGymLV) // gym_lv_defeated
		ELSE
			CLEO_CALL WriteGlobalVar 0 (8157 1)() // player_been_taught_moveLV
		ENDIF
		WHILE IS_CHAR_FIGHTING scplayer
			WAIT 0
			SET_CHAR_ANIM_SPEED scplayer "FightD_M" 1.67 //now it's not a caress
			IF IS_BUTTON_JUST_PRESSED PAD1 RIGHTSHOULDER2
				iEstilo += 1
				WHILE IS_BUTTON_JUST_PRESSED PAD1 RIGHTSHOULDER2
					WAIT 0
				ENDWHILE
			ENDIF
			IF IS_BUTTON_JUST_PRESSED PAD1 LEFTSHOULDER2
				iEstilo -= 1
				WHILE IS_BUTTON_JUST_PRESSED PAD1 LEFTSHOULDER2
					WAIT 0
				ENDWHILE
			ENDIF
			IF iEstilo > 7
				iEstilo = 5
			ENDIF
			IF iEstilo < 5
				iEstilo = 7
			ENDIF
			IF iEstilo = 5
			AND iGymLS = TRUE
				GIVE_MELEE_ATTACK_TO_CHAR scplayer iEstilo 6
			ENDIF
			IF iEstilo = 6
			AND iGymSF = TRUE
				GIVE_MELEE_ATTACK_TO_CHAR scplayer iEstilo 6
			ENDIF
			IF iEstilo = 7
			AND iGymLV = TRUE
				GIVE_MELEE_ATTACK_TO_CHAR scplayer iEstilo 6
			ENDIF
			IF iGymLS = FALSE
			AND iGymSF = FALSE
			AND iGymLV = FALSE
				GIVE_MELEE_ATTACK_TO_CHAR scplayer 15 6 //Punch and kick //This will be ignored if scplayer learn any new moves.
			ENDIF
		ENDWHILE
	ENDIF

	IF iGaloCinza = TRUE
	AND IS_CHAR_FIGHTING scplayer
		WHILE IS_CHAR_FIGHTING scplayer
			WAIT 0
			SET_CHAR_ANIM_SPEED scplayer "FightD_M" 1.67 //now it's not a caress
			IF IS_BUTTON_JUST_PRESSED PAD1 RIGHTSHOULDER2
				iEstilo += 1
				WHILE IS_BUTTON_JUST_PRESSED PAD1 RIGHTSHOULDER2
					WAIT 0
				ENDWHILE
			ENDIF
			IF IS_BUTTON_JUST_PRESSED PAD1 LEFTSHOULDER2
				iEstilo -= 1
				WHILE IS_BUTTON_JUST_PRESSED PAD1 LEFTSHOULDER2
					WAIT 0
				ENDWHILE
			ENDIF
			IF iEstilo > 7
				iEstilo = 5
			ENDIF
			IF iEstilo < 5
				iEstilo = 7
			ENDIF
			GIVE_MELEE_ATTACK_TO_CHAR scplayer iEstilo 6
		ENDWHILE
	ENDIF

	IF TEST_CHEAT GALOCINZA //Galo cinza de pescoço fino tem fama de grande brigador em rinhas de galo.
	OR TEST_CHEAT GRAYROOSTER //Thin-necked gray rooster is famous for being a great fighter in cockfights.
		IF iGaloCinza = FALSE
			iGaloCinza = TRUE
			PRINT_HELP CHEAT1 //Cheat activated
		ELSE
			iGaloCinza = FALSE
			PRINT_HELP CHEAT8 //Cheat deactivated
		ENDIF
	ENDIF

	GOTO main_loop
}

SCRIPT_END

{
	LVAR_INT var value //In
	LVAR_INT scriptSpace finalOffset

	WriteGlobalVar:
	READ_MEMORY 0x00468D5E 4 1 (scriptSpace)
	finalOffset = var * 4
	finalOffset += scriptSpace
	WRITE_MEMORY finalOffset 4 (value) FALSE
	CLEO_RETURN 0 ()
}

{
	LVAR_INT var //In
	LVAR_INT value scriptSpace finalOffset

	ReadGlobalVar:
	READ_MEMORY 0x00468D5E 4 1 (scriptSpace)
	finalOffset = var * 4
	finalOffset += scriptSpace
	READ_MEMORY finalOffset 4 FALSE (value)
	CLEO_RETURN 0 (value)
}
